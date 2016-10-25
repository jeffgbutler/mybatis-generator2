package org.mybatis.generator2.dom.java;

public class Parameter extends JavaDomNode {

    private String name;
    private String type;
    private boolean isVarargs;
    private boolean isFinal;

    private Parameter() {
        super();
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
    
    public boolean isFinal() {
        return isFinal;
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
        
        public Builder isVarArgs(boolean isVarArgs) {
            parameter.isVarargs = isVarArgs;
            return this;
        }
        
        public Builder isFinal(boolean isFinal) {
            parameter.isFinal = isFinal;
            return this;
        }
        
        public Parameter build() {
            return parameter;
        }
    }
}
