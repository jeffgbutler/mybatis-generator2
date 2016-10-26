package org.mybatis.generator2.dom.java;

import java.util.Optional;

public class FieldDefinition extends JavaDomNode {

    private JavaDoc javaDoc;
    private Modifiers modifiers;
    private String type;
    private String name;
    private String initializationString;
    
    private FieldDefinition() {
        super();
    }

    @Override
    public void accept(JavaDomVisitor visitor) {
        visitor.visit(this);
    }

    public Optional<JavaDoc> getJavaDoc() {
        return Optional.ofNullable(javaDoc);
    }
    
    public Optional<Modifiers> getModifiers() {
        return Optional.ofNullable(modifiers);
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public Optional<String> getInitializationString() {
        return Optional.ofNullable(initializationString);
    }
    
    public static class Builder {
        private FieldDefinition fieldDefinition = new FieldDefinition();

        public Builder(String name, String type) {
            fieldDefinition.name = name;
            fieldDefinition.type = type;
        }
        
        public Builder withInitializationString(String initializationString) {
            fieldDefinition.initializationString = initializationString;
            return this;
        }
        
        public Builder withModifiers(Modifiers modifiers) {
            modifiers.parent = fieldDefinition;
            fieldDefinition.modifiers = modifiers;
            return this;
        }
        
        public Builder withJavaDoc(JavaDoc javaDoc) {
            fieldDefinition.javaDoc = javaDoc;
            return this;
        }
        
        public FieldDefinition build() {
            return fieldDefinition;
        }
    }
}
