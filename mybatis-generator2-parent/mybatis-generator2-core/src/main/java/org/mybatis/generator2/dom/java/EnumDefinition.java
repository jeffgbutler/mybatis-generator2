package org.mybatis.generator2.dom.java;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class EnumDefinition extends AbstractTypeOrEnum<EnumDefinition> {

    private List<EnumConstantDefinition> enumConstantDefinitions = new ArrayList<>();
    private List<ConstructorDefinition> constructorDefinitions = new ArrayList<>();
    
    private EnumDefinition(String name) {
        super(name);
    }
    
    public Stream<EnumConstantDefinition> enumConstants() {
        return enumConstantDefinitions.stream();
    }

    public Stream<ConstructorDefinition> constructors() {
        return constructorDefinitions.stream();
    }

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
        return JavaNodeType.ENUM;
    }
    
    @Override
    public boolean allowsModifier(JavaModifier javaModifier) {
        boolean rc;
        
        switch (javaModifier) {
        case PUBLIC:
        case PROTECTED:
        case PRIVATE:
        case STRICTFP:
            rc = true;
            break;
        default:
            rc = false;
        }
        
        return rc;
    }

    @Override
    public EnumDefinition deepCopy() {
        return new Builder(getName())
                .withJavaDoc(javaDoc == null ? null : javaDoc.deepCopy())
                .withModifiers(modifiers.stream())
                .withSuperInterfaces(superInterfaces())
                .withEnumConstants(enumConstants().map(EnumConstantDefinition::deepCopy))
                .withFields(fields().map(FieldDefinition::deepCopy))
                .withConstructors(constructors().map(ConstructorDefinition::deepCopy))
                .withMethods(methods().map(MethodDefinition::deepCopy))
                .withClassDefinitions(classes().map(ClassDefinition::deepCopy))
                .withEnumDefinitions(enums().map(EnumDefinition::deepCopy))
                .withInterfaceDefinitions(interfaces().map(InterfaceDefinition::deepCopy))
                .build();
    }
    
    public static class Builder extends AbstractTypeOrEnumBuilder<Builder, EnumDefinition> {
        private EnumDefinition enumDefinition;
        
        public Builder(String name) {
            enumDefinition = new EnumDefinition(name);
        }
        
        public Builder withConstructor(ConstructorDefinition constructor) {
            constructor.parent = enumDefinition;
            constructor.name = enumDefinition.getName();
            enumDefinition.constructorDefinitions.add(constructor);
            return this;
        }

        public Builder withConstructors(Stream<ConstructorDefinition> constructors) {
            constructors.forEach(this::withConstructor);
            return this;
        }

        public Builder withEnumConstant(EnumConstantDefinition enumConstant) {
            enumConstant.parent = enumDefinition;
            enumDefinition.enumConstantDefinitions.add(enumConstant);
            return this;
        }
        
        public Builder withEnumConstants(Stream<EnumConstantDefinition> enumConstants) {
            enumConstants.forEach(this::withEnumConstant);
            return this;
        }

        @Override
        protected EnumDefinition getConcreteItem() {
            return enumDefinition;
        }

        @Override
        protected Builder getThis() {
            return this;
        }
        
        public EnumDefinition build() {
            return getConcreteItem();
        }
    }
}
