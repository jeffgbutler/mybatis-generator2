package org.mybatis.generator2.db.node;

import static org.mybatis.generator2.util.Messages.getString;
import static org.mybatis.generator2.util.StringUtils.composeFullyQualifiedTableName;
import static org.mybatis.generator2.util.StringUtils.stringContainsSpace;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.StringTokenizer;

import org.mybatis.generator2.config.Context;
import org.mybatis.generator2.config.TableConfiguration;
import org.mybatis.generator2.log.Log;
import org.mybatis.generator2.log.LogFactory;
import org.mybatis.generator2.util.JDBCUtils;

public class DatabaseIntrospector {

    private static Log logger = LogFactory.getLog(DatabaseIntrospector.class);
    private Context context;
    private DatabaseMetaData databaseMetaData;
    private IntrospectionContext introspectionContext = new IntrospectionContext();

    private DatabaseIntrospector() {
    }

    public void introspectTables() {
        context.getTables().forEach(t -> introspectTable(t));
    }

    public IntrospectionContext getIntrospectionContext() {
        return introspectionContext;
    }

    private void introspectTable(TableConfiguration tc) {
        String catalog;
        String schemaPattern;
        String tableNamePattern;
        try {
            DatabaseCapabilities capabilities = getDatabaseCapabilities(databaseMetaData);

            catalog = conformNameToDatabase(tc.getCatalog(), tc.isDelimitIdentifiers(), capabilities);
            schemaPattern = conformNameToDatabase(tc.getSchemaPattern(), tc.isDelimitIdentifiers(), capabilities);
            tableNamePattern = conformNameToDatabase(tc.getTableNamePattern(), tc.isDelimitIdentifiers(), capabilities);

            if (tc.isWildcardEscapingEnabled()) {
                schemaPattern = escapeWildcards(schemaPattern, capabilities);
                tableNamePattern = escapeWildcards(tableNamePattern, capabilities);
            }

            if (logger.isTraceEnabled()) {
                String fullTableName = composeFullyQualifiedTableName(catalog, schemaPattern, tableNamePattern, '.');
                logger.trace(getString("Tracing.1", fullTableName)); //$NON-NLS-1$
            }

            getColumns(catalog, schemaPattern, tableNamePattern);
            calculatePrimaryKeys();
        } catch (SQLException e) {
            logger.error("SQLException introspecting tables", e);
        }
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
        } else if (capabilities.storesLowerCaseIdentifiers) {
            answer = name == null ? null : name.toLowerCase();
        } else if (capabilities.storesUpperCaseIdentifiers) {
            answer = name == null ? null : name.toUpperCase();
        } else {
            answer = name;
        }

        return answer;
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
                sb.append(capabilities.searchStringEscape);
            }
            sb.append(token);
        }
        pattern = sb.toString();
        return pattern;
    }

    private void getColumns(String catalog, String schemaPattern, String tableNamePattern) throws SQLException {
        ResultSet rs = null;

        try {
            rs = databaseMetaData.getColumns(catalog, schemaPattern, tableNamePattern, "%"); //$NON-NLS-1$
            getColumns(rs);
        } finally {
            JDBCUtils.close(rs);
        }
    }

    private void getColumns(ResultSet rs) throws SQLException {
        ResultSetCapabilities capabilities = getResultSetCapabilities(rs.getMetaData());

        while (rs.next()) {
            IntrospectedColumn introspectedColumn = new IntrospectedColumn();

            introspectedColumn.dataType = rs.getInt("DATA_TYPE"); //$NON-NLS-1$
            introspectedColumn.columnSize = rs.getInt("COLUMN_SIZE"); //$NON-NLS-1$
            introspectedColumn.columnName = rs.getString("COLUMN_NAME"); //$NON-NLS-1$
            introspectedColumn.nullable = rs.getInt("NULLABLE") == DatabaseMetaData.columnNullable; //$NON-NLS-1$
            introspectedColumn.decimalDigits = rs.getInt("DECIMAL_DIGITS"); //$NON-NLS-1$
            introspectedColumn.remarks = rs.getString("REMARKS"); //$NON-NLS-1$
            introspectedColumn.columnDefault = rs.getString("COLUMN_DEF"); //$NON-NLS-1$
            introspectedColumn.ordinalPosition = rs.getInt("ORDINAL_POSITION"); //$NON-NLS-1$
            introspectedColumn.typeName = rs.getString("TYPE_NAME"); //$NON-NLS-1$

            if (capabilities.supportsIsAutoIncrement) {
                introspectedColumn.isAutoIncrement = "YES".equals(rs.getString("IS_AUTOINCREMENT")); //$NON-NLS-1$ //$NON-NLS-2$
            }

            if (capabilities.supportsIsGeneratedColumn) {
                introspectedColumn.isGeneratedColumn = "YES".equals(rs.getString("IS_GENERATEDCOLUMN")); //$NON-NLS-1$ //$NON-NLS-2$
            }

            IntrospectedTable introspectedTable = introspectionContext.getIntrospectedTable(rs.getString("TABLE_CAT"), //$NON-NLS-1$
                    rs.getString("TABLE_SCHEM"), //$NON-NLS-1$
                    rs.getString("TABLE_NAME")); //$NON-NLS-1$

            introspectedTable.addColumn(introspectedColumn);

            if (logger.isTraceEnabled()) {
                logger.trace(getString("Tracing.2", //$NON-NLS-1$
                        introspectedColumn.columnName, introspectedColumn.dataType,
                        introspectedTable.getFullTableName()));
            }
        }
    }

    private DatabaseCapabilities getDatabaseCapabilities(DatabaseMetaData metaData) throws SQLException {
        DatabaseCapabilities capabilities = new DatabaseCapabilities();

        capabilities.storesLowerCaseIdentifiers = metaData.storesLowerCaseIdentifiers();
        capabilities.storesUpperCaseIdentifiers = metaData.storesUpperCaseIdentifiers();
        capabilities.searchStringEscape = metaData.getSearchStringEscape();

        return capabilities;
    }

    private ResultSetCapabilities getResultSetCapabilities(ResultSetMetaData metaData) throws SQLException {
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

    private void calculatePrimaryKeys() {
        PrimaryKeyCalculator c = new PrimaryKeyCalculator(databaseMetaData);
        introspectionContext.getTables().forEach(t -> c.calculatePrimaryKey(t));
    }

    public static class Builder {
        private DatabaseIntrospector introspector = new DatabaseIntrospector();

        public Builder withContext(Context context) {
            introspector.context = context;
            return this;
        }

        public Builder withDatabaseMetaData(DatabaseMetaData databaseMetaData) {
            introspector.databaseMetaData = databaseMetaData;
            return this;
        }

        public DatabaseIntrospector build() {
            return introspector;
        }
    }

    static class DatabaseCapabilities {
        String searchStringEscape;
        boolean storesUpperCaseIdentifiers;
        boolean storesLowerCaseIdentifiers;
    }

    static class ResultSetCapabilities {
        boolean supportsIsAutoIncrement;
        boolean supportsIsGeneratedColumn;
    }
}
