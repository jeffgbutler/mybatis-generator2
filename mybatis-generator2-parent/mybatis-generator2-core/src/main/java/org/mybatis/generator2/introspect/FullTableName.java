package org.mybatis.generator2.introspect;

import static org.mybatis.generator2.util.StringUtils.stringHasValue;

public class FullTableName {
    private String tableName;
    private String catalog;
    private String schema;
    private String remarks;
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
        if (obj == null || !(obj instanceof FullTableName)) {
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

    private void updateFullyQualifiedTableName() {
        StringBuilder sb = new StringBuilder();

        if (stringHasValue(catalog)) {
            sb.append(catalog);
            sb.append('.');
        }

        if (stringHasValue(schema)) {
            sb.append(schema);
            sb.append('.');
        } else {
            if (sb.length() > 0) {
                sb.append('.');
            }
        }

        sb.append(tableName);

        fullyQualifiedTableName = sb.toString();
    }
    
    public String getRemarks() {
        return remarks;
    }

    public static FullTableName from(String catalog, String schema, String tableName, String remarks) {
        FullTableName fullTableName = new FullTableName();
        fullTableName.catalog = catalog;
        fullTableName.schema = schema;
        fullTableName.tableName = tableName;
        fullTableName.remarks = remarks;
        fullTableName.updateFullyQualifiedTableName();
        return fullTableName;
    }
}
