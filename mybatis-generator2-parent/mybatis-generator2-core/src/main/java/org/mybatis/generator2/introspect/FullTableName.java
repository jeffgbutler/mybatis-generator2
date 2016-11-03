package org.mybatis.generator2.introspect;

import static org.mybatis.generator2.util.StringUtils.stringHasValue;

public class FullTableName {
    private String catalog;
    private String schema;
    private String tableName;
    private String escapedSchema;
    private String escapedTableName;
    private String remarks;
    private boolean wasEscaped;
    private String fullyQualifiedTableName;

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
        if (obj == null) {
            return false;
        }
        
        if (!(obj instanceof FullTableName)) {
            return false;
        }

        return ((FullTableName) obj).fullyQualifiedTableName.equals(this.fullyQualifiedTableName);
    }

    @Override
    public int hashCode() {
        return fullyQualifiedTableName.hashCode();
    }

    @Override
    public String toString() {
        return fullyQualifiedTableName;
    }

    public String getRemarks() {
        return remarks;
    }

    public String getIntrospectionSchema() {
        return wasEscaped ? escapedSchema : schema;
    }

    public String getIntrospectionTableName() {
        return wasEscaped ? escapedTableName : tableName;
    }

    public static class Builder {
        private FullTableName fullTableName = new FullTableName();
        
        public Builder withCatalog(String catalog) {
            fullTableName.catalog = catalog;
            return this;
        }
        
        public Builder withSchema(String schema) {
            fullTableName.schema = schema;
            return this;
        }
        
        public Builder withTableName(String tableName) {
            fullTableName.tableName = tableName;
            return this;
        }
        
        public Builder withRemarks(String remarks) {
            fullTableName.remarks = remarks;
            return this;
        }

        public Builder wasEscaped(boolean wasEscaped) {
            fullTableName.wasEscaped = wasEscaped;
            return this;
        }
        
        public Builder withEscapedSchema(String escapedSchema) {
            fullTableName.escapedSchema = escapedSchema;
            return this;
        }
        
        public Builder withEscapedTableName(String escapedTableName) {
            fullTableName.escapedTableName = escapedTableName;
            return this;
        }

        public FullTableName build() {
            updateFullyQualifiedTableName();
            return fullTableName;
        }

        private void updateFullyQualifiedTableName() {
            StringBuilder sb = new StringBuilder();

            if (stringHasValue(fullTableName.catalog)) {
                sb.append(fullTableName.catalog);
                sb.append('.');
            }

            if (stringHasValue(fullTableName.schema)) {
                sb.append(fullTableName.schema);
                sb.append('.');
            } else {
                if (sb.length() > 0) {
                    sb.append('.');
                }
            }

            sb.append(fullTableName.tableName);

            fullTableName.fullyQualifiedTableName = sb.toString();
        }
    }
}
