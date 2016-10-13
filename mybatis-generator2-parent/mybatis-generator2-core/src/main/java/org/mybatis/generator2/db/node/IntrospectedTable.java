package org.mybatis.generator2.db.node;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

public class IntrospectedTable {

    private IntrospectionContext parent;
    private FullTableName fullTableName;
    private Map<String, IntrospectedColumn> columns = new LinkedHashMap<>();
    
    private IntrospectedTable() {
    }

    public FullTableName getFullTableName() {
        return fullTableName;
    }
    
    void addColumn(IntrospectedColumn introspectedColumn) {
        columns.put(introspectedColumn.columnName, introspectedColumn);
    }
    
    public Stream<IntrospectedColumn> getAllColumns() {
        return columns.values().stream();
    }
    
    public Stream<IntrospectedColumn> getPrimaryKeyColumns() {
        return columns.values().stream().filter(c -> c.isPrimaryKeyColumn());
    }
    
    public boolean hasPrimaryKey() {
        return columns.values().stream().anyMatch(c -> c.isPrimaryKeyColumn());
    }
    
    public Stream<IntrospectedColumn> getBlobColumns() {
        return columns.values().stream().filter(c -> c.isBlobColumn());
    }
    
    public boolean hasBlobColumns() {
        return columns.values().stream().anyMatch(c -> c.isBlobColumn());
    }

    public boolean hasBaseColumns() {
        return columns.values().stream().anyMatch(c -> c.isBaseColumn());
    }
    
    public boolean hasAnyColumns() {
        return columns.size() > 0;
    }
    
    public IntrospectedColumn getColumn(String name) {
        return columns.get(name);
    }

    public static class Builder {
        private IntrospectedTable introspectedTable = new IntrospectedTable();
        
        public Builder withParent(IntrospectionContext parent) {
            introspectedTable.parent = parent;
            return this;
        }
        
        public Builder withFullTableName(FullTableName fullTableName) {
            introspectedTable.fullTableName = fullTableName;
            return this;
        }
        
        public IntrospectedTable build() {
            return introspectedTable;
        }
    }
}
