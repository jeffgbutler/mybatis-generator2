package org.mybatis.generator2.dom.java;

public class InterfaceDefinition extends AbstractTypeOrEnum<InterfaceDefinition> {

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

    @Override
    public InterfaceDefinition deepCopy() {
        return new Builder(name)
                .withJavaDoc(javaDoc == null ? null : javaDoc.deepCopy())
                .withModifiers(modifiers.stream())
                .withSuperInterfaces(superInterfaces())
                .withFields(fields().map(FieldDefinition::deepCopy))
                .withMethods(methods().map(MethodDefinition::deepCopy))
                .withClassDefinitions(classes().map(ClassDefinition::deepCopy))
                .withEnumDefinitions(enums().map(EnumDefinition::deepCopy))
                .withInterfaceDefinitions(interfaces().map(InterfaceDefinition::deepCopy))
                .build();
    }
    
    public static class Builder extends AbstractTypeOrEnumBuilder<Builder, InterfaceDefinition> {
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
