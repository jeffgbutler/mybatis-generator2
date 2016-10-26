package org.mybatis.generator2.dom.java;

import java.util.Optional;

public class ClassDefinition extends AbstractTypeOrEnum {
    
    private String superClass;

    private ClassDefinition() {
        super();
    }
    
    public Optional<String> getSuperClass() {
        return Optional.ofNullable(superClass);
    }

    /**
     * This method will call the visitor for fields, methods,
     * classes, enums, and interfaces.  This visitor will NOT be called
     * for the super class or super interfaces as is would be impossible to
     * distinguish between them in the visitor. 
     */
    @Override
    public void accept(JavaDomVisitor visitor) {
        if (visitor.visit(this)) {
            fields().forEach(f -> f.accept(visitor));
            methods().forEach(m -> m.accept(visitor));
            classes().forEach(t -> t.accept(visitor));
            enums().forEach(t -> t.accept(visitor));
            interfaces().forEach(t -> t.accept(visitor));
        }
        visitor.endVisit(this);
    }

    @Override
    public JavaNodeType getNodeType() {
        return JavaNodeType.CLASS;
    }
    
    @Override
    public boolean allowsModifier(JavaModifier javaModifier) {
        boolean rc;
        
        switch (javaModifier) {
        case PUBLIC:
        case PROTECTED:
        case PRIVATE:
        case ABSTRACT:
        case STATIC:
        case FINAL:
        case STRICTFP:
            rc = true;
            break;
        default:
            rc = false;
        }
        
        return rc;
    }

    public static class Builder extends AbstractTypeOrEnumBuilder<Builder> {
        private ClassDefinition classDefinition = new ClassDefinition();
        
        public Builder(String name) {
            classDefinition.name = name;
        }
        
        public Builder withSuperClass(String superClass) {
            classDefinition.superClass = superClass;
            return this;
        }

        @Override
        protected ClassDefinition getConcreteItem() {
            return classDefinition;
        }

        @Override
        protected Builder getThis() {
            return this;
        }
        
        public ClassDefinition build() {
            return getConcreteItem();
        }
    }
}
