package org.mybatis.generator2.introspect;

import static org.mybatis.generator2.util.Messages.getString;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.mybatis.generator2.config.TableConfiguration;
import org.mybatis.generator2.log.Log;
import org.mybatis.generator2.log.LogFactory;
import org.mybatis.generator2.util.Messages.MessageId;

public class IntrospectedTable {

    private static final Log logger = LogFactory.getLog(IntrospectedTable.class);

    private TableConfiguration tableConfiguration;
    private FullTableName fullTableName;
    private List<IntrospectedColumn> columns = new ArrayList<>();

    private IntrospectedTable() {
    }

    public FullTableName getFullTableName() {
        return fullTableName;
    }

    public Stream<IntrospectedColumn> getAllColumns() {
        return columns.stream();
    }

    public Stream<IntrospectedColumn> getPrimaryKeyColumns() {
        return columns.stream().filter(IntrospectedColumn::isPrimaryKeyColumn);
    }

    public boolean hasPrimaryKey() {
        return columns.stream().anyMatch(IntrospectedColumn::isPrimaryKeyColumn);
    }

    public Stream<IntrospectedColumn> getBlobColumns() {
        return columns.stream().filter(IntrospectedColumn::isBlobColumn);
    }

    public boolean hasBlobColumns() {
        return columns.stream().anyMatch(IntrospectedColumn::isBlobColumn);
    }

    public Stream<IntrospectedColumn> getBaseColumns() {
        return columns.stream().filter(IntrospectedColumn::isBaseColumn);
    }

    public boolean hasBaseColumns() {
        return columns.stream().anyMatch(IntrospectedColumn::isBaseColumn);
    }

    public boolean hasAnyColumns() {
        return !columns.isEmpty();
    }

    public Optional<IntrospectedColumn> getColumn(String name) {
        return columns.stream().filter(c -> name.equals(c.getColumnName())).findFirst();
    }

    public static IntrospectedTable from(IntrospectedTable introspectedTable, List<IntrospectedColumn> filteredColumns) {
        IntrospectedTable newIntrospectedTable = new IntrospectedTable();
        newIntrospectedTable.columns = filteredColumns;
        newIntrospectedTable.fullTableName = introspectedTable.fullTableName;
        newIntrospectedTable.tableConfiguration = introspectedTable.tableConfiguration;
        return newIntrospectedTable;
    }

    public static IntrospectedTable from(TableConfiguration tableConfiguration, FullTableName fullTableName,
            DatabaseMetaData databaseMetaData) throws SQLException {
        return new IntrospectingBuilder(tableConfiguration, fullTableName, databaseMetaData).build();
    }

    private static class IntrospectingBuilder {
        
        private IntrospectedTable introspectedTable = new IntrospectedTable();
        private FullTableName fullTableName;
        private DatabaseMetaData databaseMetaData;
        
        public IntrospectingBuilder(TableConfiguration tableConfiguration, FullTableName fullTableName,
                DatabaseMetaData databaseMetaData) {
            introspectedTable.tableConfiguration = tableConfiguration;
            introspectedTable.fullTableName = fullTableName;
            this.fullTableName = fullTableName;
            this.databaseMetaData = databaseMetaData;
        }
        
        public IntrospectedTable build() throws SQLException {
            calculateColumns();
            calculatePrimaryKey();
        
            return introspectedTable;
        }
        
        private void calculateColumns() throws SQLException {
            String catalog = fullTableName.getCatalog();
            String schema = fullTableName.getIntrospectionSchema();
            String tableName = fullTableName.getIntrospectionTableName();
            try (ResultSet rs = databaseMetaData.getColumns(catalog, schema, tableName, "%")) { //$NON-NLS-1$
                handleColumnResultSet(rs, introspectedTable);
            }
        }

        private void handleColumnResultSet(ResultSet rs, IntrospectedTable introspectedTable)
                throws SQLException {
            ResultSetCapabilities capabilities = ResultSetCapabilities.from(rs.getMetaData());

            while (rs.next()) {
                introspectedTable.columns.add(handleColumnRow(rs, capabilities));
            }
        }

        private IntrospectedColumn handleColumnRow(ResultSet rs, 
                ResultSetCapabilities capabilities) throws SQLException {
            IntrospectedColumn introspectedColumn = IntrospectedColumn.fromCurrentRow(rs, capabilities);

            logger.trace(() -> getString(MessageId.TRACING_2, introspectedColumn.getColumnName(),
                    introspectedColumn.getDataType(), introspectedTable.getFullTableName()));

            return introspectedColumn;
        }

        private void calculatePrimaryKey() throws SQLException {
            try (ResultSet rs = databaseMetaData.getPrimaryKeys(fullTableName.getCatalog(), fullTableName.getSchema(),
                    fullTableName.getTableName())) {
                handlePrimaryKeyResultSet(rs, introspectedTable);
            }
        }

        private void handlePrimaryKeyResultSet(ResultSet rs, IntrospectedTable introspectedTable)
                throws SQLException {
            while (rs.next()) {
                handlePrimaryKeyRow(rs, introspectedTable);
            }
        }

        private void handlePrimaryKeyRow(ResultSet rs, IntrospectedTable introspectedTable) throws SQLException {
            String columnName = rs.getString("COLUMN_NAME"); //$NON-NLS-1$
            short keySequence = rs.getShort("KEY_SEQ"); //$NON-NLS-1$

            Optional<IntrospectedColumn> column = introspectedTable.getColumn(columnName);
            column.ifPresent(c -> {
                c.isPrimaryKeyColumn = true;
                c.keySequence = keySequence;
            });

            if (!column.isPresent()) {
                logger.warn(() -> getString(MessageId.WARNING_29, columnName, introspectedTable));
            }
        }
    }
}
