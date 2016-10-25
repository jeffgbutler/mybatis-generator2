package org.mybatis.generator2.dom.java;

public class InterfaceDefinition extends AbstractTypeOrEnum {

    private InterfaceDefinition() {
        super();
    }

    @Override
    public void accept(JavaDomVisitor visitor) {
        if (visitor.visit(this)) {
            fieldDefinitions.stream().forEach(f -> f.accept(visitor));
            methodDefinitions.stream().forEach(m -> m.accept(visitor));
            classDefinitions.stream().forEach(t -> t.accept(visitor));
            enumDefinitions.stream().forEach(t -> t.accept(visitor));
            interfaceDefinitions.stream().forEach(t -> t.accept(visitor));
        }
    }

    public static class Builder extends AbstractTypeOrEnumBuilder<Builder> {
        private InterfaceDefinition interfaceDefinition = new InterfaceDefinition();
        
        @Override
        protected InterfaceDefinition getConcreteItem() {
            return interfaceDefinition;
        }

        @Override
        protected Builder getThis() {
            return this;
        }
        
        public InterfaceDefinition build() {
            return getConcreteItem();
        }
    }
}
