package org.mybatis.generator2.dom.java;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class ModifierSet extends JavaDomNode {

    private Set<JavaModifier> javaModifiers = new HashSet<>();
    
    ModifierSet(JavaDomNode parent) {
        super();
        this.parent = parent;
    }

    void addJavaModifier(JavaModifier javaModifier) {
        javaModifiers.add(javaModifier);
    }
    
    @Override
    public void accept(JavaDomVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public JavaNodeType getNodeType() {
        return JavaNodeType.MODIFIERS;
    }
    
    @Override
    public boolean allowsModifier(JavaModifier javaModifier) {
        return false;
    }

    public boolean isDefault() {
        return javaModifiers.contains(JavaModifier.DEFAULT);
    }
    
    public boolean isStatic() {
        return javaModifiers.contains(JavaModifier.STATIC);
    }
    
    public boolean isAbstract() {
        return javaModifiers.contains(JavaModifier.ABSTRACT);
    }
    
    public boolean isNative() {
        return javaModifiers.contains(JavaModifier.NATIVE);
    }
    
    public boolean isFinal() {
        return javaModifiers.contains(JavaModifier.FINAL);
    }
    
    /**
     * Returns modifiers in order recommended by JLS
     * @return
     */
    public Stream<JavaModifier> javaModifiers() {
        return javaModifiers.stream().sorted();
    }
}
