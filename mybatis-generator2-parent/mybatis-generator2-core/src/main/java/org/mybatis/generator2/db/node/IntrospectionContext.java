package org.mybatis.generator2.db.node;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class IntrospectionContext {

    private Map<FullTableName, IntrospectedTable> tables = new HashMap<>();
    
    IntrospectionContext() {
    }
    
    /**
     * This method will build a new instance if there is not one already
     * in the map.  This method is intended to be called from the introspector only.
     * 
     * @param catalog the catalog as returned from the JDBC driver
     * @param schema the schema as returned from the JDBC driver
     * @param tableName the table name as returned from the JDBC driver
     * @return
     */
    IntrospectedTable getIntrospectedTable(String catalog, String schema, String tableName) {
        FullTableName fullTableName = new FullTableName.Builder()
                .withCatalog(catalog)
                .withSchema(schema)
                .withTableName(tableName)
                .build();

        IntrospectedTable introspectedTable = tables.get(fullTableName);
        if (introspectedTable == null) {
            introspectedTable = new IntrospectedTable.Builder()
                    .withFullTableName(fullTableName)
                    .withParent(this)
                    .build();
            
            tables.put(fullTableName, introspectedTable);
        }
        
        return introspectedTable;
    }
    
    public Stream<IntrospectedTable> getTables() {
        return tables.values().stream();
    }
}
