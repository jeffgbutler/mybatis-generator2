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
            fields().forEach(f -> f.accept(visitor));
            methods().forEach(m -> m.accept(visitor));
            classes().forEach(t -> t.accept(visitor));
            enums().forEach(t -> t.accept(visitor));
            interfaces().forEach(t -> t.accept(visitor));
        }
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

    public static class Builder extends AbstractTypeOrEnumBuilder<Builder> {
        private EnumDefinition enumDefinition = new EnumDefinition();
        
        public Builder(String name) {
            enumDefinition.name = name;
        }
        
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
