package org.mybatis.generator2.workflow;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.mybatis.generator2.config.Configuration;
import org.mybatis.generator2.config.Context;
import org.mybatis.generator2.exception.IntrospectionException;
import org.mybatis.generator2.introspect.IntrospectedColumn;
import org.mybatis.generator2.introspect.IntrospectedContext;
import org.mybatis.generator2.introspect.IntrospectedTable;
import org.mybatis.generator2.introspect.IntrospectedTableConfiguration;
import org.mybatis.generator2.workflow.temp.DomObject;
import org.mybatis.generator2.workflow.temp.GeneratedFile;
import org.mybatis.generator2.workflow.temp.PreparedTable;

public class DefaultJavaWorkflow implements DefaultWorkflow {

    private DefaultJavaWorkflow() {
    }

    public static DefaultJavaWorkflow newInstance() {
        return new DefaultJavaWorkflow();
    }

    @Override
    public Stream<IntrospectedContext> introspect(Configuration configuration) throws IntrospectionException {
        List<IntrospectedContext> contexts = new ArrayList<>();
        
        for (Context context : configuration.contexts()) {
            contexts.add(IntrospectedContext.from(context));
        }

        return contexts.stream();
    }

    @Override
    public Stream<IntrospectedTable> filterTables(IntrospectedContext introspectedContext) {
        return introspectedContext.getConfigs().flatMap(IntrospectedTableConfiguration::tables);
    }

    @Override
    public IntrospectedTable filterColumns(IntrospectedTable introspectedTable) {
        List<IntrospectedColumn> filteredColumns = introspectedTable.allColumns()
                .filter(c -> true)
                .collect(Collectors.toList());
        
        return IntrospectedTable.from(introspectedTable, filteredColumns);
    }

    @Override
    public PreparedTable prepare(IntrospectedTable introspectedTable) {
        return PreparedTable.from(introspectedTable);
    }
    
    @Override
    public Stream<DomObject> generate(PreparedTable preparedTable) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public GeneratedFile render(DomObject domObject) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void write(GeneratedFile generatedFile) {
        // TODO Auto-generated method stub
    }
}
