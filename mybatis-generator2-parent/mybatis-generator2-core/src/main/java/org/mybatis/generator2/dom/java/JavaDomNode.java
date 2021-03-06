package org.mybatis.generator2.dom.java;

public abstract class JavaDomNode<T> {
    public enum JavaNodeType {
        ARGUMENT,
        CLASS,
        COMPILATION_UNIT,
        CONSTRUCTOR,
        ENUM,
        ENUM_CONSTANT,
        FIELD,
        IMPORT,
        INTERFACE,
        JAVADOC,
        METHOD,
        PARAMETER;
    }
    
    JavaDomNode<?> parent;

    public JavaDomNode<?> getParent() {
        return parent;
    }
    
    public abstract T deepCopy();
    public abstract void accept(JavaDomVisitor visitor);
    public abstract JavaNodeType getNodeType();
    public abstract boolean allowsModifier(JavaModifier javaModifier);
}
