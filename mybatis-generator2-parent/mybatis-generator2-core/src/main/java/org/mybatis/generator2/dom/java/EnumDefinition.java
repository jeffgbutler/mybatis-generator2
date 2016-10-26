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
            enumConstants().forEach(e -> e.accept(visitor));
            fields().forEach(f -> f.accept(visitor));
            methods().forEach(m -> m.accept(visitor));
            classes().forEach(t -> t.accept(visitor));
            enums().forEach(t -> t.accept(visitor));
            interfaces().forEach(t -> t.accept(visitor));
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
