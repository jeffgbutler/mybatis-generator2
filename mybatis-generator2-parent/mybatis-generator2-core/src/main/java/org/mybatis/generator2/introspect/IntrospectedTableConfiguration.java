package org.mybatis.generator2.introspect;

import static org.mybatis.generator2.util.Messages.getString;
import static org.mybatis.generator2.util.StringUtils.stringContainsSpace;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Stream;

import org.mybatis.generator2.config.TableConfiguration;
import org.mybatis.generator2.log.Log;
import org.mybatis.generator2.log.LogFactory;
import org.mybatis.generator2.util.Messages.MessageId;

public class IntrospectedTableConfiguration {
    private static Log logger = LogFactory.getLog(IntrospectedTableConfiguration.class);

    private TableConfiguration tableConfiguration;
    private List<IntrospectedTable> tables = new ArrayList<>();

    private IntrospectedTableConfiguration() {
    }

    public Stream<IntrospectedTable> tables() {
        return tables.stream();
    }

    public static IntrospectedTableConfiguration from(TableConfiguration tableConfiguration,
            DatabaseMetaData databaseMetaData) throws SQLException {
        return new Builder(tableConfiguration, databaseMetaData).build();
    }

    private static class Builder {
        
        private IntrospectedTableConfiguration introspectedTableConfiguration = new IntrospectedTableConfiguration();
        private TableConfiguration tableConfiguration;
        private DatabaseMetaData databaseMetaData;
        private String escapedSchemaPattern;
        private String escapedTableNamePattern;
        
        public Builder(TableConfiguration tableConfiguration,
                DatabaseMetaData databaseMetaData) {
            this.tableConfiguration = tableConfiguration;
            this.databaseMetaData = databaseMetaData;
        }
        
        public IntrospectedTableConfiguration build() throws SQLException {
            introspectedTableConfiguration.tableConfiguration = tableConfiguration;
            introspectTable();
            return introspectedTableConfiguration;
        }

        private void introspectTable()
                throws SQLException {
            DatabaseCapabilities capabilities = DatabaseCapabilities.from(databaseMetaData);

            String catalog = conformNameToDatabase(tableConfiguration.getCatalog(), tableConfiguration.isDelimitIdentifiers(),
                    capabilities);
            String schemaPattern = conformNameToDatabase(tableConfiguration.getSchemaPattern(),
                    tableConfiguration.isDelimitIdentifiers(), capabilities);
            String tableNamePattern = conformNameToDatabase(tableConfiguration.getTableNamePattern(),
                    tableConfiguration.isDelimitIdentifiers(), capabilities);

            if (tableConfiguration.isWildcardEscapingEnabled()) {
                escapedSchemaPattern = escapeWildcards(schemaPattern, capabilities);
                escapedTableNamePattern = escapeWildcards(tableNamePattern, capabilities);
            } else {
                escapedSchemaPattern = schemaPattern;
                escapedTableNamePattern = tableNamePattern;
            }

            logger.trace(() -> getString(MessageId.TRACING_6, catalog, escapedSchemaPattern, escapedTableNamePattern));

            List<FullTableName> tables = getTables(catalog, escapedSchemaPattern, escapedTableNamePattern,
                    databaseMetaData);
            for (FullTableName table : tables) {
                IntrospectedTable introspectedTable = IntrospectedTable.from(tableConfiguration, table, databaseMetaData);
                introspectedTableConfiguration.tables.add(introspectedTable);
            }
        }

        private String conformNameToDatabase(String name, boolean forceDelimit,
                DatabaseCapabilities capabilities) {
            String answer;

            if (forceDelimit) {
                // user has requested that the names are delimited, so for this
                // API
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
                if ("_".equals(token) //$NON-NLS-1$
                        || "%".equals(token)) { //$NON-NLS-1$
                    sb.append(capabilities.getSearchStringEscape());
                }
                sb.append(token);
            }
            return sb.toString();
        }

        private List<FullTableName> getTables(String catalog, String schemaPattern, String tableNamePattern,
                DatabaseMetaData databaseMetaData) throws SQLException {
            List<FullTableName> tables = null;
            try (ResultSet rs = databaseMetaData.getTables(catalog, schemaPattern, tableNamePattern, null)) {
                tables = handleResultSet(rs);
            }

            return tables;
        }

        private List<FullTableName> handleResultSet(ResultSet rs) throws SQLException {
            List<FullTableName> tables = new ArrayList<>();
            while (rs.next()) {
                tables.add(handleRow(rs));
            }

            return tables;
        }

        private FullTableName handleRow(ResultSet rs) throws SQLException {
            String catalog = rs.getString("TABLE_CAT"); //$NON-NLS-1$
            String schema = rs.getString("TABLE_SCHEM"); //$NON-NLS-1$
            String tableName = rs.getString("TABLE_NAME"); //$NON-NLS-1$
            String remarks = rs.getString("REMARKS"); //$NON-NLS-1$

            FullTableName fullTableName = new FullTableName.Builder()
                    .withCatalog(catalog)
                    .withSchema(schema)
                    .withTableName(tableName)
                    .withRemarks(remarks)
                    .wasEscaped(tableConfiguration.isWildcardEscapingEnabled())
                    .withEscapedSchema(escapedSchemaPattern)
                    .withEscapedTableName(escapedTableNamePattern)
                    .build();

            logger.trace(() -> getString(MessageId.TRACING_5, fullTableName)); // $NON-NLS-1$

            return fullTableName;
        }
    }
}
