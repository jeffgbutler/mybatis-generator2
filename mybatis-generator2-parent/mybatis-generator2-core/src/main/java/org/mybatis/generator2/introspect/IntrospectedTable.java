package org.mybatis.generator2.introspect;

import static org.mybatis.generator2.util.Messages.getString;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.mybatis.generator2.log.Log;
import org.mybatis.generator2.log.LogFactory;
import org.mybatis.generator2.util.Messages.MessageId;

public class IntrospectedTable {
    
    private static final Log logger = LogFactory.getLog(IntrospectedTable.class);

    private FullTableName fullTableName;
    private Map<String, IntrospectedColumn> columns = new LinkedHashMap<>();
    
    private IntrospectedTable() {
    }

    public FullTableName getFullTableName() {
        return fullTableName;
    }
    
    public Stream<IntrospectedColumn> getAllColumns() {
        return columns.values().stream();
    }
    
    public Stream<IntrospectedColumn> getPrimaryKeyColumns() {
        return columns.values().stream().filter(IntrospectedColumn::isPrimaryKeyColumn);
    }
    
    public boolean hasPrimaryKey() {
        return columns.values().stream().anyMatch(IntrospectedColumn::isPrimaryKeyColumn);
    }
    
    public Stream<IntrospectedColumn> getBlobColumns() {
        return columns.values().stream().filter(IntrospectedColumn::isBlobColumn);
    }
    
    public boolean hasBlobColumns() {
        return columns.values().stream().anyMatch(IntrospectedColumn::isBlobColumn);
    }

    public Stream<IntrospectedColumn> getBaseColumns() {
        return columns.values().stream().filter(IntrospectedColumn::isBaseColumn);
    }

    public boolean hasBaseColumns() {
        return columns.values().stream().anyMatch(IntrospectedColumn::isBaseColumn);
    }
    
    public boolean hasAnyColumns() {
        return columns.size() > 0;
    }
    
    public Optional<IntrospectedColumn> getColumn(String name) {
        return Optional.ofNullable(columns.get(name));
    }

    public static IntrospectedTable from(FullTableName fullTableName, DatabaseMetaData databaseMetaData) throws SQLException {
        IntrospectedTable introspectedTable = new IntrospectedTable();
        introspectedTable.fullTableName = fullTableName;

        calculateColumns(fullTableName, databaseMetaData, introspectedTable);
        calculatePrimaryKey(fullTableName, databaseMetaData, introspectedTable);

        return introspectedTable;
    }

    private static void calculateColumns(FullTableName fullTableName, DatabaseMetaData databaseMetaData,
            IntrospectedTable introspectedTable) throws SQLException {
        try (ResultSet rs = databaseMetaData.getColumns(
                fullTableName.getCatalog(),
                fullTableName.getSchema(),
                fullTableName.getTableName(), "%")) { //$NON-NLS-1$
            handleColumnResultSet(rs, introspectedTable);
        }
    }

    private static void handleColumnResultSet(ResultSet rs, IntrospectedTable introspectedTable) throws SQLException {
        ResultSetCapabilities capabilities = ResultSetCapabilities.from(rs.getMetaData());

        while (rs.next()) {
            handleColumnRow(rs, introspectedTable, capabilities);
        }
    }

    private static void handleColumnRow(ResultSet rs, IntrospectedTable introspectedTable,
            ResultSetCapabilities capabilities) throws SQLException {
        IntrospectedColumn introspectedColumn = IntrospectedColumn.fromCurrentFow(rs, capabilities);

        introspectedTable.columns.put(introspectedColumn.getColumnName(), introspectedColumn);

        logger.trace(() -> getString(MessageId.TRACING_2,
                introspectedColumn.getColumnName(), introspectedColumn.getDataType(),
                introspectedTable.getFullTableName()));
    }

    private static void calculatePrimaryKey(FullTableName fullTableName, DatabaseMetaData databaseMetaData,
            IntrospectedTable introspectedTable) throws SQLException {
        try (ResultSet rs = databaseMetaData.getPrimaryKeys(
                fullTableName.getCatalog(),
                fullTableName.getSchema(),
                fullTableName.getTableName())) {
            handlePrimaryKeyResultSet(rs, introspectedTable);
        }
    }

    private static void handlePrimaryKeyResultSet(ResultSet rs, IntrospectedTable introspectedTable) throws SQLException {
        while (rs.next()) {
            handlePrimaryKeyRow(rs, introspectedTable);
        }
    }

    private static void handlePrimaryKeyRow(ResultSet rs, IntrospectedTable introspectedTable) throws SQLException {
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
