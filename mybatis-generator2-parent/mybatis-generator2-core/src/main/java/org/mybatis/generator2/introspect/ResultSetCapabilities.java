package org.mybatis.generator2.introspect;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;

class ResultSetCapabilities {
    private boolean supportsIsAutoIncrement;
    private boolean supportsIsGeneratedColumn;

    private ResultSetCapabilities() {

    }

    public boolean supportsIsAutoIncrement() {
        return supportsIsAutoIncrement;
    }

    public boolean supportsIsGeneratedColumn() {
        return supportsIsGeneratedColumn;
    }

    public static ResultSetCapabilities from(ResultSetMetaData metaData) throws SQLException {
        ResultSetCapabilities capabilities = new ResultSetCapabilities();

        int colCount = metaData.getColumnCount();
        for (int i = 1; i <= colCount; i++) {
            if ("IS_AUTOINCREMENT".equals(metaData.getColumnName(i))) { //$NON-NLS-1$
                capabilities.supportsIsAutoIncrement = true;
            }
            if ("IS_GENERATEDCOLUMN".equals(metaData.getColumnName(i))) { //$NON-NLS-1$
                capabilities.supportsIsGeneratedColumn = true;
            }
        }

        return capabilities;
    }
}