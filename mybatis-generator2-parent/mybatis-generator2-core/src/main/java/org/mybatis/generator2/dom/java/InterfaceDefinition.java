package org.mybatis.generator2.dom.java;

public class InterfaceDefinition extends AbstractTypeOrEnum {

    private InterfaceDefinition() {
        super();
    }

    @Override
    public void accept(JavaDomVisitor visitor) {
        if (visitor.visit(this)) {
            acceptChildren(visitor);
        }
        visitor.endVisit(this);
    }

    @Override public void acceptConstructors(JavaDomVisitor visitor) {
        // no constructors in interfaces
        return;
    }

    @Override
    public JavaNodeType getNodeType() {
        return JavaNodeType.INTERFACE;
    }
    
    @Override
    public boolean allowsModifier(JavaModifier javaModifier) {
        boolean rc;
        
        switch (javaModifier) {
        case PUBLIC:
        case STRICTFP:
            rc = true;
            break;
            
        case PROTECTED:
        case PRIVATE:
            rc = parent.getNodeType() == JavaNodeType.CLASS;
            break;
            
        case STATIC:
            rc = parent.getNodeType() != JavaNodeType.COMPILATION_UNIT;
            break;
            
        default:
            rc = false;
        }
        
        return rc;
    }

    public static class Builder extends AbstractTypeOrEnumBuilder<Builder> {
        private InterfaceDefinition interfaceDefinition = new InterfaceDefinition();
        
        public Builder(String name) {
            interfaceDefinition.name = name;
        }
        
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
