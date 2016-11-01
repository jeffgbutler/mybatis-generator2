package org.mybatis.generator2.dom.java;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class Parameter extends JavaDomNode<Parameter> {

    private Set<JavaModifier> modifiers = new HashSet<>();
    private String name;
    private String type;
    private boolean isVarargs;

    private Parameter() {
        super();
    }

    public ModifierSet getModifierSet() {
        return new ModifierSet(this, modifiers);
    }
    
    public String getName() {
        return name;
    }
    
    public String getType() {
        return type;
    }
    
    public boolean isVarArgs() {
        return isVarargs;
    }
    
    @Override
    public void accept(JavaDomVisitor visitor) {
        visitor.visit(this);
    }
    
    @Override
    public JavaNodeType getNodeType() {
        return JavaNodeType.PARAMETER;
    }
    
    @Override
    public boolean allowsModifier(JavaModifier javaModifier) {
        return javaModifier == JavaModifier.FINAL;
    }
    
    @Override
    public Parameter deepCopy() {
        return new Builder(type, name)
                .isVarArgs(isVarargs)
                .withModifiers(modifiers.stream())
                .build();
    }

    public static Parameter of(String type, String name) {
        return new Builder(type, name).build();
    }

    public static class Builder {
        private Parameter parameter = new Parameter();
        
        public Builder(String type, String name) {
            parameter.type = type;
            parameter.name = name;
        }

        public Builder withModifier(JavaModifier javaModifier) {
            parameter.modifiers.add(javaModifier);
            return this;
        }
        
        public Builder withModifiers(Stream<JavaModifier> javaModifiers) {
            javaModifiers.forEach(this::withModifier);
            return this;
        }

        public Builder isVarArgs(boolean isVarArgs) {
            parameter.isVarargs = isVarArgs;
            return this;
        }
        
        public Parameter build() {
            return parameter;
        }
    }
}
