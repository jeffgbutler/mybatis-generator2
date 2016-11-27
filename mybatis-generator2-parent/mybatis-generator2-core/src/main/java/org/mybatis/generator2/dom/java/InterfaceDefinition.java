package org.mybatis.generator2.dom.java;

public class InterfaceDefinition extends AbstractTypeOrEnum<InterfaceDefinition> {

    private InterfaceDefinition(String name) {
        super(name);
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
            rc = isParentClassOrEnum();
            break;
            
        case STATIC:
            rc = isParentClassOrEnumOrInterface();
            break;
            
        default:
            rc = false;
        }
        
        return rc;
    }
    
    private boolean isParentClassOrEnum() {
        return parent != null &&
                (isParentClass() || isParentEnum());
    }

    private boolean isParentClassOrEnumOrInterface() {
        return parent != null &&
                (isParentInterface() || isParentClassOrEnum());
    }
    
    private boolean isParentClass() {
        return parent.getNodeType() == JavaNodeType.CLASS;
    }
    
    private boolean isParentEnum() {
        return parent.getNodeType() == JavaNodeType.ENUM;
    }
    
    private boolean isParentInterface() {
        return parent.getNodeType() == JavaNodeType.INTERFACE;
    }

    @Override
    public InterfaceDefinition deepCopy() {
        return new Builder(getName())
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
        private InterfaceDefinition interfaceDefinition;
        
        public Builder(String name) {
            interfaceDefinition = new InterfaceDefinition(name);
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
