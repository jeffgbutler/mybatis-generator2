package org.mybatis.generator2.dom.java;

public class InterfaceDefinition extends AbstractTypeOrEnum {

    private InterfaceDefinition() {
        super();
    }

    @Override
    public void accept(JavaDomVisitor visitor) {
        if (visitor.visit(this)) {
            fields().forEach(f -> f.accept(visitor));
            methods().forEach(m -> m.accept(visitor));
            classes().forEach(t -> t.accept(visitor));
            enums().forEach(t -> t.accept(visitor));
            interfaces().forEach(t -> t.accept(visitor));
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
