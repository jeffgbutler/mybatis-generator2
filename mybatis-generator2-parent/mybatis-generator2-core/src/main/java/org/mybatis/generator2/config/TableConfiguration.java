package org.mybatis.generator2.config;

public class TableConfiguration {
    
    private boolean delimitIdentifiers;
    private String catalog;
    private String schemaPattern;
    private String tableNamePattern;
    private boolean wildcardEscapingEnabled;
    
    private TableConfiguration() {
    }

    public boolean isDelimitIdentifiers() {
        return delimitIdentifiers;
    }

    public String getCatalog() {
        return catalog;
    }

    public String getSchemaPattern() {
        return schemaPattern;
    }

    public String getTableNamePattern() {
        return tableNamePattern;
    }

    public boolean isWildcardEscapingEnabled() {
        return wildcardEscapingEnabled;
    }
    
    public static TableConfiguration from(String tableNamePattern) {
        TableConfiguration configuration = new TableConfiguration();
        configuration.tableNamePattern = tableNamePattern;
        return configuration;
    }
    
    public static class Builder {
        private TableConfiguration configuration = new TableConfiguration();
        
        public Builder withCatalog(String catalog) {
            configuration.catalog = catalog;
            return this;
        }
        
        public Builder withSchemaPattern(String schemaPattern) {
            configuration.schemaPattern = schemaPattern;
            return this;
        }
        
        public Builder withTableNamePattern(String tableNamePattern) {
            configuration.tableNamePattern = tableNamePattern;
            return this;
        }
        
        public Builder withWildcardEscapingEnabled(boolean wildcardEscapingEnabled) {
            configuration.wildcardEscapingEnabled = wildcardEscapingEnabled;
            return this;
        }
        
        public TableConfiguration build() {
            return configuration;
        }
    }
}
