package org.mybatis.generator2.introspect;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.mybatis.generator2.config.Context;
import org.mybatis.generator2.config.TableConfiguration;
import org.mybatis.generator2.exception.IntrospectionException;
import org.mybatis.generator2.log.Log;
import org.mybatis.generator2.log.LogFactory;

public class IntrospectedContext {
    private static Log log = LogFactory.getLog(IntrospectedContext.class);

    private Context context;
    private List<IntrospectedTableConfiguration> tableConfigurations = new ArrayList<>();
    
    private IntrospectedContext() {
    }
    
    public Stream<IntrospectedTableConfiguration> getConfigs() {
        return tableConfigurations.stream();
    }
    
    public static IntrospectedContext from(Context context) throws IntrospectionException {
        IntrospectedContext introspectedContext = new IntrospectedContext();
        introspectedContext.context = context;
        
        Connection connection = null;
        try {
            connection = context.getConnectionFactory().getConnection();
            DatabaseMetaData databaseMetaData = connection.getMetaData();
 
            for (TableConfiguration config : context.getTables()) {
                introspectedContext.tableConfigurations.add(IntrospectedTableConfiguration.from(config, databaseMetaData));
            }
        } catch (SQLException e) {
            throw new IntrospectionException("SQL Exception during introspection", e);
        } finally {
            closeConnection(connection);
        }
        
        return introspectedContext;
    }
    
    private static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                log.error(() -> "SQLException on connection close", e);
            }
        }
    }
}
