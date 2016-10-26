package org.mybatis.generator2.dom.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Modifiers extends JavaDomNode {

    private List<JavaModifier> modifiers = new ArrayList<>();
    
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
    
    /**
     * Returns modifiers in order recommended by JLS
     * @return
     */
    public Stream<JavaModifier> modifiers() {
        return modifiers.stream().sorted();
    }

    public Modifiers with(JavaModifier... modifiers) {
        return new Builder()
                .withModifiers(modifiers())
                .withModifiers(modifiers)
                .build();
    }
    
    public static Modifiers of(JavaModifier... modifiers) {
        return new Builder()
                .withModifiers(modifiers)
                .build();
    }
    
    public static class Builder {
        private Modifiers modifiers = new Modifiers();
        
        public Builder withModifier(JavaModifier modifier) {
            modifiers.modifiers.add(modifier);
            return this;
        }

        public Builder withModifiers(JavaModifier... modifiers) {
            Arrays.stream(modifiers).forEach(this.modifiers.modifiers::add);
            return this;
        }
        
        public Builder withModifiers(Stream<JavaModifier> modifiers) {
            modifiers.forEach(this.modifiers.modifiers::add);
            return this;
        }
        
        public Modifiers build() {
            return modifiers;
        }
    }
}
