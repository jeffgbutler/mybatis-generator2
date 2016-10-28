package org.mybatis.generator2.dom.java;

public abstract class JavaDomNode {
    public enum JavaNodeType {
        ARGUMENT,
        CLASS,
        COMPILATION_UNIT,
        ENUM,
        ENUM_CONSTANT,
        FIELD,
        IMPORT,
        INTERFACE,
        JAVADOC,
        METHOD,
        MODIFIERS,
        PARAMETER;
    }
    
    JavaDomNode parent;

    public JavaDomNode getParent() {
        return parent;
    }
    
    public abstract void accept(JavaDomVisitor visitor);
    public abstract JavaNodeType getNodeType();
    public abstract boolean allowsModifier(JavaModifier javaModifier);
}
