package org.mybatis.generator2.db.node;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class IntrospectionContext {

    private Map<FullTableName, IntrospectedTable> tables = new HashMap<>();
    
    IntrospectionContext() {
    }
    
    void addTable(IntrospectedTable table) {
        tables.put(table.getFullTableName(), table);
    }
    
    public Stream<IntrospectedTable> getTables() {
        return tables.values().stream();
    }
}
