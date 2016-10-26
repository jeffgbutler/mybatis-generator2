package org.mybatis.generator2.dom.java;

import java.util.ArrayList;
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

    /**
     * Returns modifiers in order recommended by JLS
     * @return
     */
    public Stream<JavaModifier> modifiers() {
        return modifiers.stream().sorted();
    }

    public Modifiers with(JavaModifier modifier) {
        return new Builder()
                .withModifiers(modifiers())
                .withModifier(modifier)
                .build();
    }
    
    public static Modifiers of(JavaModifier modifier) {
        return new Builder()
                .withModifier(modifier)
                .build();
    }
    
    public static class Builder {
        private Modifiers modifiers = new Modifiers();
        
        public Builder withModifier(JavaModifier modifier) {
            modifiers.modifiers.add(modifier);
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
