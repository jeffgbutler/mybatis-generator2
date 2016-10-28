package org.mybatis.generator2.dom.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Modifiers extends JavaDomNode {

    private List<JavaModifier> javaModifiers = new ArrayList<>();
    
    private Modifiers() {
        super();
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
    
    /**
     * Returns modifiers in order recommended by JLS
     * @return
     */
    public Stream<JavaModifier> javaModifiers() {
        return javaModifiers.stream().sorted();
    }

    public Modifiers with(JavaModifier... javaModifiers) {
        return new Builder()
                .withJavaModifiers(javaModifiers())
                .withJavaModifiers(javaModifiers)
                .build();
    }
    
    public static Modifiers of(JavaModifier... javaModifiers) {
        return new Builder()
                .withJavaModifiers(javaModifiers)
                .build();
    }
    
    public static class Builder {
        private Modifiers modifiers = new Modifiers();
        
        public Builder withJavaModifiers(JavaModifier... modifiers) {
            return withJavaModifiers(Arrays.stream(modifiers));
        }
        
        public Builder withJavaModifiers(Stream<JavaModifier> modifiers) {
            modifiers.forEach(this.modifiers.javaModifiers::add);
            return this;
        }
        
        public Modifiers build() {
            return modifiers;
        }
    }
}
