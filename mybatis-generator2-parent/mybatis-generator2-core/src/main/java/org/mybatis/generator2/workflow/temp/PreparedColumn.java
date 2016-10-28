package org.mybatis.generator2.workflow.temp;

import org.mybatis.generator2.introspect.IntrospectedColumn;

public class PreparedColumn {

    private PreparedColumn() {
        // TODO Auto-generated constructor stub
    }

    public static PreparedColumn from(IntrospectedColumn introspectedColumn) {
        return new PreparedColumn();
    }
}
