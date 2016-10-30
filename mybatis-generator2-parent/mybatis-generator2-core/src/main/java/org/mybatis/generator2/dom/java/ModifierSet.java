package org.mybatis.generator2.dom.java;

import java.util.Set;
import java.util.stream.Stream;

/**
 * This isn't a true DOM node, really just a container.  But is does
 * have some of the characteristics of a DOM node in that it has a parent
 * and can accept a visitor.
 * 
 * @author Jeff Butler
 *
 */
public class ModifierSet {

    private Set<JavaModifier> modifiers;
    private JavaDomNode<?> parent;
    
    ModifierSet(JavaDomNode<?> parent, Set<JavaModifier> modifiers) {
        super();
        this.parent = parent;
        this.modifiers = modifiers;
    }

    public JavaDomNode<?> getParent() {
        return parent;
    }
    
    public void accept(JavaDomVisitor visitor) {
        visitor.visit(this);
    }

    public boolean isDefault() {
        return modifiers.contains(JavaModifier.DEFAULT);
    }
    
    public boolean isStatic() {
        return modifiers.contains(JavaModifier.STATIC);
    }
    
    public boolean isAbstract() {
        return modifiers.contains(JavaModifier.ABSTRACT);
    }
    
    public boolean isNative() {
        return modifiers.contains(JavaModifier.NATIVE);
    }
    
    public boolean isFinal() {
        return modifiers.contains(JavaModifier.FINAL);
    }
    
    /**
     * Returns modifiers in order recommended by JLS
     * @return
     */
    public Stream<JavaModifier> javaModifiers() {
        return modifiers.stream().sorted();
    }
}
