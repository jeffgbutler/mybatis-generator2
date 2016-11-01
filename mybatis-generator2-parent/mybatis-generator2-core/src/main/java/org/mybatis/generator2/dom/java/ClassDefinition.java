package org.mybatis.generator2.dom.java;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class ClassDefinition extends AbstractTypeOrEnum<ClassDefinition> {
    
    private String superClass;
    private List<ConstructorDefinition> constructorDefinitions = new ArrayList<>();

    private ClassDefinition() {
        super();
    }
    
    public Optional<String> getSuperClass() {
        return Optional.ofNullable(superClass);
    }
    
    public Stream<ConstructorDefinition> constructors() {
        return constructorDefinitions.stream();
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
            acceptChildren(visitor);
        }
        visitor.endVisit(this);
    }
    
    @Override public void acceptConstructors(JavaDomVisitor visitor) {
        constructorDefinitions.forEach(c -> c.accept(visitor));
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
    
    @Override
    public ClassDefinition deepCopy() {
        return new Builder(name)
                .withJavaDoc(javaDoc == null ? null : javaDoc.deepCopy())
                .withModifiers(modifiers.stream())
                .withSuperInterfaces(superInterfaces())
                .withSuperClass(superClass)
                .withFields(fields().map(FieldDefinition::deepCopy))
                .withConstructors(constructors().map(ConstructorDefinition::deepCopy))
                .withMethods(methods().map(MethodDefinition::deepCopy))
                .withClassDefinitions(classes().map(ClassDefinition::deepCopy))
                .withEnumDefinitions(enums().map(EnumDefinition::deepCopy))
                .withInterfaceDefinitions(interfaces().map(InterfaceDefinition::deepCopy))
                .build();
    }

    public static class Builder extends AbstractTypeOrEnumBuilder<Builder, ClassDefinition> {
        private ClassDefinition classDefinition = new ClassDefinition();
        
        public Builder(String name) {
            classDefinition.name = name;
        }
        
        public Builder withSuperClass(String superClass) {
            classDefinition.superClass = superClass;
            return this;
        }

        public Builder withConstructor(ConstructorDefinition constructor) {
            constructor.parent = classDefinition;
            constructor.name = classDefinition.name;
            classDefinition.constructorDefinitions.add(constructor);
            return this;
        }

        public Builder withConstructors(Stream<ConstructorDefinition> constructors) {
            constructors.forEach(this::withConstructor);
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
