package org.mybatis.generator2.config;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Context {
    
    private List<TableConfiguration> tables = new ArrayList<>();
    
    public Stream<TableConfiguration> getTables() {
        return tables.stream();
    }

    public void addTable(TableConfiguration tableConfiguration) {
        tables.add(tableConfiguration);
    }
}
