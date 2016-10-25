package org.mybatis.generator2.dom.java;

import java.util.ArrayList;
import java.util.List;

public class MethodDefinition extends JavaDomNode {

    private JavaDoc javaDoc;
    private JavaVisibility visibility;
    private boolean isStatic;
    private boolean isDefault;
    private boolean isAbstract;
    private boolean isFinal;
    private String returnType;
    private String name;
    private List<Parameter> parameters = new ArrayList<>();
    private List<String> bodyLines = new ArrayList<>();
    
    private MethodDefinition() {
        super();
    }

    @Override
    public void accept(JavaDomVisitor visitor) {
        visitor.visit(this);
    }
}
