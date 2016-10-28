package org.mybatis.generator2.workflow;

import java.util.stream.Stream;

import org.mybatis.generator2.config.Configuration;
import org.mybatis.generator2.exception.IntrospectionException;
import org.mybatis.generator2.introspect.IntrospectedContext;
import org.mybatis.generator2.introspect.IntrospectedTable;
import org.mybatis.generator2.workflow.temp.DomObject;
import org.mybatis.generator2.workflow.temp.GeneratedFile;
import org.mybatis.generator2.workflow.temp.PreparedTable;

public interface DefaultWorkflow extends Workflow {

    @Override
    default void executeWorkflow(Configuration configuration) throws IntrospectionException {
        introspect(configuration)
            .flatMap(this::filterTables)
            .map(this::filterColumns)
            .map(this::prepare)
            .flatMap(this::generate)
            .map(this::render)
            .forEach(this::write);
    }
    
    Stream<IntrospectedContext> introspect(Configuration configuration) throws IntrospectionException;

    /**
     * Filter out any tables that should be ignored.  For example, if a table
     * configuration specifies a wildcard name for simplicity, but we only want a few of
     * the tables.
     * 
     * @param introspectedContext
     * @return
     */
    Stream<IntrospectedTable> filterTables(IntrospectedContext introspectedContext);
    
    /**
     * Return a new introspceted table with the ignored columns filtered out
     * 
     * @param introspectedTable
     * @return
     */
    IntrospectedTable filterColumns(IntrospectedTable introspectedTable);
    
    /**
     * Return a prepared table from the introspected table.  Preparation
     * involves calculating field and object names, data types, packages, etc.
     * This is where any column overrides would be applied.
     *
     * @param introspectedTable
     * @return
     */
    PreparedTable prepare(IntrospectedTable introspectedTable);
    
    Stream<DomObject> generate(PreparedTable preparedTable);
    GeneratedFile render(DomObject domObject);
    void write(GeneratedFile generatedFile);
}
