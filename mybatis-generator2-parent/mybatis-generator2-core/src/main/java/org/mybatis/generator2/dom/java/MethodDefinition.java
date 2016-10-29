package org.mybatis.generator2.dom.java;

import java.util.Optional;

/**
 * @author Jeff Butler
 *
 */
public class MethodDefinition extends AbstractMethodDefinition {

    private String returnType;
    private String name;
    
    private MethodDefinition() {
        super();
    }

    @Override
    public void accept(JavaDomVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public JavaNodeType getNodeType() {
        return JavaNodeType.METHOD;
    }
    
    @Override
    public boolean allowsModifier(JavaModifier javaModifier) {
        boolean rc;
        switch (parent.getNodeType()) {
        case CLASS:
        case ENUM:
            rc = isModifierAllowedForClassMethod(javaModifier);
            break;
        
        case INTERFACE:
            rc = isModifierAllowedForInterfaceMethod(javaModifier);
            break;
            
        default:
            rc = false;
        }

        return rc;
    }
    
    private boolean isModifierAllowedForClassMethod(JavaModifier javaModifier) {
        boolean rc;
        
        switch (javaModifier) {
        case PUBLIC:
        case PROTECTED:
        case PRIVATE:
        case ABSTRACT:
        case STATIC:
        case FINAL:
        case SYNCHRONIZED:
        case NATIVE:
        case STRICTFP:
            rc = true;
            break;
        
        default:
            rc = false;
        }
        
        return rc;
    }

    private boolean isModifierAllowedForInterfaceMethod(JavaModifier javaModifier) {
        boolean rc;
        
        switch (javaModifier) {
        case DEFAULT:
        case STATIC:
        case STRICTFP:
            rc = true;
            break;
        
        default:
            rc = false;
        }
        
        return rc;
    }

    public Optional<String> getReturnType() {
        return Optional.ofNullable(returnType);
    }

    public String getName() {
        return name;
    }

    public boolean allowsBodyLines() {
        boolean rc;
        
        switch (parent.getNodeType()) {
        case CLASS:
        case ENUM:
            rc = isBodyAllowedInClass();
            break;
            
        case INTERFACE:
            rc = isBodyAllowedInInterface();
            break;
            
        default:
            rc = false;
        }
        
        return rc;
    }

    private boolean isBodyAllowedInClass() {
        return !(getModifierSet().isAbstract() || getModifierSet().isNative());
    }

    private boolean isBodyAllowedInInterface() {
        return getModifierSet().isDefault() || getModifierSet().isStatic();
    }

    public static class Builder extends AbstractMethodDefinitionBuilder<Builder> {
        private MethodDefinition methodDefinition = new MethodDefinition();
        
        public Builder(String returnType, String name) {
            methodDefinition.returnType = returnType;
            methodDefinition.name = name;
        }

        @Override
        public Builder getThis() {
            return this;
        }
        
        @Override
        public MethodDefinition getMethod() {
            return methodDefinition;
        }
        
        public MethodDefinition build() {
            return methodDefinition;
        }
    }
}
