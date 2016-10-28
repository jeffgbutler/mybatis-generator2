package org.mybatis.generator2.workflow.temp;

import java.util.List;
import java.util.stream.Collectors;

import org.mybatis.generator2.introspect.IntrospectedTable;

public class PreparedTable {

    private List<PreparedColumn> preparedColumns;
    
    private PreparedTable() {
        // TODO Auto-generated constructor stub
    }

    public static PreparedTable from(IntrospectedTable introspectedTable) {
        PreparedTable preparedTable = new PreparedTable();
        
        preparedTable.preparedColumns = introspectedTable.allColumns()
                .map(PreparedColumn::from)
                .collect(Collectors.toList());
        
        return preparedTable;
    }
}
