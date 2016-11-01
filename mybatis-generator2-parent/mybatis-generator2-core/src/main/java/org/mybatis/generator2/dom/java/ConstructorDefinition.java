package org.mybatis.generator2.dom.java;

/**
 * Constructor names are derived from their parent
 *  
 * @author Jeff Butler
 *
 */
public class ConstructorDefinition extends AbstractMethodDefinition<ConstructorDefinition> {

    // name is set by the parent
    String name;
    
    private ConstructorDefinition() {
        super();
    }

    @Override
    public void accept(JavaDomVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public JavaNodeType getNodeType() {
        return JavaNodeType.CONSTRUCTOR;
    }
    
    @Override
    public boolean allowsModifier(JavaModifier javaModifier) {
        boolean rc;
        switch (parent.getNodeType()) {
        case CLASS:
            rc = isModifierAllowedForClassConstructor(javaModifier);
            break;
        case ENUM:
            rc = isModifierAllowedForEnumConstructor(javaModifier);
            break;
        
        default:
            rc = false;
        }

        return rc;
    }
    
    private boolean isModifierAllowedForClassConstructor(JavaModifier javaModifier) {
        boolean rc;
        
        switch (javaModifier) {
        case PUBLIC:
        case PROTECTED:
        case PRIVATE:
            rc = true;
            break;
            
        default:
            rc = false;
        }
        
        return rc;
    }

    private boolean isModifierAllowedForEnumConstructor(JavaModifier javaModifier) {
        return javaModifier == JavaModifier.PRIVATE;
    }
    
    @Override
    public ConstructorDefinition deepCopy() {
        return new Builder()
                .withJavaDoc(javaDoc == null ? null : javaDoc.deepCopy())
                .withModifiers(modifiers.stream())
                .withParameters(parameters().map(Parameter::deepCopy))
                .withBodyLines(bodyLines())
                .withExceptions(exceptions())
                .build();
    }
    
    public String getName() {
        return name;
    }

    public static class Builder extends AbstractMethodDefinitionBuilder<Builder, ConstructorDefinition> {
        private ConstructorDefinition constructorDefinition = new ConstructorDefinition();
        
        public ConstructorDefinition build() {
            return constructorDefinition;
        }

        @Override
        public Builder getThis() {
            return this;
        }

        @Override
        public ConstructorDefinition getMethod() {
            return constructorDefinition;
        }
    }
}
