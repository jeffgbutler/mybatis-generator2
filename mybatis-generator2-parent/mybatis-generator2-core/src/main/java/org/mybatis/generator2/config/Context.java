package org.mybatis.generator2.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Context {
    
    private List<TableConfiguration> tables = new ArrayList<>();
    private ConnectionFactory connectionFactory;
    
    public List<TableConfiguration> getTables() {
        return Collections.unmodifiableList(tables);
    }

    public void addTable(TableConfiguration tableConfiguration) {
        tables.add(tableConfiguration);
    }

    public ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }
}
