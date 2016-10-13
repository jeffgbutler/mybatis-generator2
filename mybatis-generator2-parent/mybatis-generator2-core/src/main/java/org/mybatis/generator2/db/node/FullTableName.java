package org.mybatis.generator2.db.node;

import static org.mybatis.generator2.util.StringUtils.composeFullyQualifiedTableName;

public class FullTableName {
    private String tableName;
    private String catalog;
    private String schema;

    private FullTableName() {
    }

    public String getCatalog() {
        return catalog;
    }

    public String getSchema() {
        return schema;
    }

    public String getTableName() {
        return tableName;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof FullTableName)) {
            return false;
        }

        return obj.toString().equals(this.toString());
    }

    @Override
    public int hashCode() {
        return composeFullyQualifiedTableName(catalog, schema, tableName, '.').hashCode();
    }

    @Override
    public String toString() {
        return composeFullyQualifiedTableName(catalog, schema, tableName, '.');
    }
    
    public static class Builder {
        private FullTableName fullTableName = new FullTableName();
        
        public Builder withTableName(String tableName) {
            fullTableName.tableName = tableName;
            return this;
        }

        public Builder withSchema(String schema) {
            fullTableName.schema = schema;
            return this;
        }

        public Builder withCatalog(String catalog) {
            fullTableName.catalog = catalog;
            return this;
        }
        
        public FullTableName build() {
            return fullTableName;
        }
    }
}
