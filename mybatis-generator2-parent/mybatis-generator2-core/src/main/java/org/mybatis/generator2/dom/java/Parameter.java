package org.mybatis.generator2.dom.java;

import java.util.Optional;

public class Parameter extends JavaDomNode {

    private Modifiers modifiers;
    private String name;
    private String type;
    private boolean isVarargs;

    private Parameter() {
        super();
    }

    public Optional<Modifiers> getModifiers() {
        return Optional.ofNullable(modifiers);
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

    public static class Builder {
        private Parameter parameter = new Parameter();
        
        public Builder(String name, String type) {
            parameter.name = name;
            parameter.type = type;
        }

        public Builder withModifiers(Modifiers modifiers) {
            modifiers.parent = parameter;
            parameter.modifiers = modifiers;
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
