package org.mybatis.generator2.dom.java;

/**
 * Constructor names are derived from their parent
 *  
 * @author Jeff Butler
 *
 */
public class ConstructorDefinition extends AbstractMethodDefinition {

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

    public static class Builder extends AbstractMethodDefinitionBuilder<Builder> {
        private ConstructorDefinition constructorDefinition = new ConstructorDefinition();
        
        public ConstructorDefinition build() {
            return constructorDefinition;
        }

        @Override
        public Builder getThis() {
            return this;
        }

        @Override
        public AbstractMethodDefinition getMethod() {
            return constructorDefinition;
        }
    }
}
