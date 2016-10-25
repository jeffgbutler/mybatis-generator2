package org.mybatis.generator2.dom.java;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class EnumDefinition extends AbstractTypeOrEnum {

    private List<EnumConstantDefinition> enumConstantDefinitions = new ArrayList<>();
    
    private EnumDefinition() {
        super();
    }
    
    public Stream<EnumConstantDefinition> enumConstants() {
        return enumConstantDefinitions.stream();
    }

    @Override
    public void accept(JavaDomVisitor visitor) {
        if (visitor.visit(this)) {
            enumConstantDefinitions.stream().forEach(e -> e.accept(visitor));
            fieldDefinitions.stream().forEach(f -> f.accept(visitor));
            methodDefinitions.stream().forEach(m -> m.accept(visitor));
            classDefinitions.stream().forEach(t -> t.accept(visitor));
            enumDefinitions.stream().forEach(t -> t.accept(visitor));
            interfaceDefinitions.stream().forEach(t -> t.accept(visitor));
        }
    }
    
    public static class Builder extends AbstractTypeOrEnumBuilder<Builder> {
        private EnumDefinition enumDefinition = new EnumDefinition();
        
        public Builder withEnumConstant(EnumConstantDefinition enumConstant) {
            enumConstant.parent = enumDefinition;
            enumDefinition.enumConstantDefinitions.add(enumConstant);
            return this;
        }
        
        public Builder withEnumConstants(Stream<EnumConstantDefinition> enumConstants) {
            enumConstants.forEach(enumConstant -> {
                enumConstant.parent = enumDefinition;
                enumDefinition.enumConstantDefinitions.add(enumConstant);
            });
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
