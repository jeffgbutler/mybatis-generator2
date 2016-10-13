package org.mybatis.generator2.db.node;

import static org.mybatis.generator2.util.Messages.getString;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mybatis.generator2.log.Log;
import org.mybatis.generator2.log.LogFactory;
import org.mybatis.generator2.util.JDBCUtils;

public class PrimaryKeyCalculator {
    private static Log logger = LogFactory.getLog(PrimaryKeyCalculator.class);
    private DatabaseMetaData databaseMetaData;
    
    PrimaryKeyCalculator(DatabaseMetaData databaseMetaData) {
        this.databaseMetaData = databaseMetaData;
    }

    void calculatePrimaryKey(IntrospectedTable introspectedTable) {
        ResultSet rs = null;

        try {
            rs = databaseMetaData.getPrimaryKeys(
                    introspectedTable.getFullTableName().getCatalog(),
                    introspectedTable.getFullTableName().getSchema(),
                    introspectedTable.getFullTableName().getTableName());

            while (rs.next()) {
                IntrospectedColumn column = introspectedTable.getColumn(rs.getString("COLUMN_NAME")); //$NON-NLS-1$
                column.isPrimaryKeyColumn = true;
                column.keySequence = rs.getShort("KEY_SEQ"); //$NON-NLS-1$
            }

        } catch (SQLException e) {
            logger.error(getString("Warning.15", introspectedTable.getFullTableName()), e); //$NON-NLS-1$
        } finally {
            JDBCUtils.close(rs);
        }
    }
}
