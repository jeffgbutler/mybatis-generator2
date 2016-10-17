package org.mybatis.generator2.db.node;

import static org.mybatis.generator2.util.Messages.getString;
import static org.mybatis.generator2.util.StringUtils.stringContainsSpace;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import org.mybatis.generator2.config.Context;
import org.mybatis.generator2.config.TableConfiguration;
import org.mybatis.generator2.log.Log;
import org.mybatis.generator2.log.LogFactory;
import org.mybatis.generator2.util.Messages.MessageId;

public class DatabaseIntrospector {

    private static Log logger = LogFactory.getLog(DatabaseIntrospector.class);
    private Context context;
    private DatabaseMetaData databaseMetaData;
    private IntrospectionContext introspectionContext = new IntrospectionContext();

    private DatabaseIntrospector() {
    }

    public void introspectTables() throws SQLException {
        List<TableConfiguration> tcs = context.getTables().collect(Collectors.toList());

        for (TableConfiguration tc : tcs) {
            introspectTable(tc);
        }
    }

    public IntrospectionContext getIntrospectionContext() {
        return introspectionContext;
    }

    private void introspectTable(TableConfiguration tc) throws SQLException {
        String catalog;
        String schemaPattern;
        String tableNamePattern;
        DatabaseCapabilities capabilities = DatabaseCapabilities.from(databaseMetaData);

        catalog = conformNameToDatabase(tc.getCatalog(), tc.isDelimitIdentifiers(), capabilities);
        schemaPattern = conformNameToDatabase(tc.getSchemaPattern(), tc.isDelimitIdentifiers(), capabilities);
        tableNamePattern = conformNameToDatabase(tc.getTableNamePattern(), tc.isDelimitIdentifiers(), capabilities);

        if (tc.isWildcardEscapingEnabled()) {
            schemaPattern = escapeWildcards(schemaPattern, capabilities);
            tableNamePattern = escapeWildcards(tableNamePattern, capabilities);
        }

        if (logger.isTraceEnabled()) {
            logger.trace(getString(MessageId.TRACING_6, catalog, schemaPattern, tableNamePattern));
        }

        List<FullTableName> tables = getTables(catalog, schemaPattern, tableNamePattern);
        for (FullTableName table : tables) {
            IntrospectedTable introspectedTable = IntrospectedTable.from(table, databaseMetaData);
            introspectionContext.addTable(introspectedTable);
        }
    }

    /**
     * Use the database's escape sequence to escape the wildcards
     * 
     * @param pattern
     * @param capabilities
     * @return
     */
    private String escapeWildcards(String pattern, DatabaseCapabilities capabilities) {
        if (pattern == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        st = new StringTokenizer(pattern, "_%", true); //$NON-NLS-1$
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if (token.equals("_") //$NON-NLS-1$
                    || token.equals("%")) { //$NON-NLS-1$
                sb.append(capabilities.getSearchStringEscape());
            }
            sb.append(token);
        }
        pattern = sb.toString();
        return pattern;
    }

    private String conformNameToDatabase(String name, boolean forceDelimit, DatabaseCapabilities capabilities) {
        String answer;

        if (forceDelimit) {
            // user has requested that the names are delimited, so for this API
            // call we
            // use exactly what they entered
            answer = name;
        } else if (stringContainsSpace(name)) {
            answer = name;
        } else if (capabilities.storesLowerCaseIdentifiers()) {
            answer = name == null ? null : name.toLowerCase();
        } else if (capabilities.storesUpperCaseIdentifiers()) {
            answer = name == null ? null : name.toUpperCase();
        } else {
            answer = name;
        }

        return answer;
    }

    private List<FullTableName> getTables(String catalog, String schemaPattern, String tableNamePattern)
            throws SQLException {
        List<FullTableName> tables = null;
        try (ResultSet rs = databaseMetaData.getTables(catalog, schemaPattern, tableNamePattern, null)) {
            tables = handleResultSet(rs);
        }

        return tables;
    }

    private List<FullTableName> handleResultSet(ResultSet rs) throws SQLException {
        List<FullTableName> tables = new ArrayList<>();
        while (rs.next()) {
            tables.add(handleRow(tables, rs));
        }
        
        return tables;
    }

    private FullTableName handleRow(List<FullTableName> tables, ResultSet rs) throws SQLException {
        String catalog = rs.getString("TABLE_CAT"); //$NON-NLS-1$
        String schema = rs.getString("TABLE_SCHEM"); //$NON-NLS-1$
        String tableName = rs.getString("TABLE_NAME"); //$NON-NLS-1$
        String remarks = rs.getString("REMARKS"); //$NON-NLS-1$

        FullTableName fullTableName = FullTableName.from(catalog, schema, tableName, remarks);

        if (logger.isTraceEnabled()) {
            logger.trace(getString(MessageId.TRACING_5, fullTableName)); //$NON-NLS-1$
        }
        
        return fullTableName;
    }

    public static DatabaseIntrospector from(Context context, DatabaseMetaData databaseMetaData) {
        DatabaseIntrospector databaseIntrospector = new DatabaseIntrospector();
        databaseIntrospector.context = context;
        databaseIntrospector.databaseMetaData = databaseMetaData;
        return databaseIntrospector;
    }
}
