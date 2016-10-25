package org.mybatis.generator2.dom.java;

public abstract class JavaDomNode {
    JavaDomNode parent;

    public JavaDomNode getParent() {
        return parent;
    }
    
    public abstract void accept(JavaDomVisitor visitor);
}
