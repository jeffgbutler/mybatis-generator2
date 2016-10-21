package org.mybatis.generator2.introspect;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;

class DatabaseCapabilities {
    private String searchStringEscape;
    private boolean storesUpperCaseIdentifiers;
    private boolean storesLowerCaseIdentifiers;
    
    private DatabaseCapabilities() {
        
    }

    public String getSearchStringEscape() {
        return searchStringEscape;
    }

    public boolean storesUpperCaseIdentifiers() {
        return storesUpperCaseIdentifiers;
    }

    public boolean storesLowerCaseIdentifiers() {
        return storesLowerCaseIdentifiers;
    }
    
    public static DatabaseCapabilities from(DatabaseMetaData metaData) throws SQLException {
        DatabaseCapabilities capabilities = new DatabaseCapabilities();

        capabilities.storesLowerCaseIdentifiers = metaData.storesLowerCaseIdentifiers();
        capabilities.storesUpperCaseIdentifiers = metaData.storesUpperCaseIdentifiers();
        capabilities.searchStringEscape = metaData.getSearchStringEscape();

        return capabilities;
    }
}