package org.mybatis.generator2.workflow;

import org.mybatis.generator2.config.Configuration;
import org.mybatis.generator2.exception.IntrospectionException;

public interface Workflow {

    void executeWorkflow(Configuration configuration) throws IntrospectionException;
}
