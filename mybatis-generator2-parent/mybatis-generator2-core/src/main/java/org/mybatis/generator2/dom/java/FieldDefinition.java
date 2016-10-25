package org.mybatis.generator2.dom.java;

import java.util.Optional;

public class FieldDefinition extends JavaDomNode {

    private JavaDoc javaDoc;
    private JavaVisibility visibility;
    private boolean isStatic;
    private boolean isFinal;
    private boolean isTransient;
    private boolean isVolatile;
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

    public JavaVisibility getVisibility() {
        return visibility;
    }

    public boolean isStatic() {
        return isStatic;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public boolean isTransient() {
        return isTransient;
    }

    public boolean isVolatile() {
        return isVolatile;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getInitializationString() {
        return initializationString;
    }
    
    public Optional<JavaDoc> getJavaDoc() {
        return Optional.ofNullable(javaDoc);
    }
    
    public static class Builder {
        private FieldDefinition fieldDefinition = new FieldDefinition();
        
        public Builder of(FieldDefinition other) {
            fieldDefinition.javaDoc = other.javaDoc;
            fieldDefinition.initializationString = other.initializationString;
            fieldDefinition.isFinal = other.isFinal;
            fieldDefinition.isStatic = other.isStatic;
            fieldDefinition.isTransient = other.isTransient;
            fieldDefinition.isVolatile = other.isVolatile;
            fieldDefinition.name = other.name;
            fieldDefinition.type = other.type;
            fieldDefinition.visibility = other.visibility;
            return this;
        }
        
        public Builder withInitializationString(String initializationString) {
            fieldDefinition.initializationString = initializationString;
            return this;
        }
        
        public Builder isFinal(boolean isFinal) {
            fieldDefinition.isFinal = isFinal;
            return this;
        }
        
        public Builder isStatic(boolean isStatic) {
            fieldDefinition.isStatic = isStatic;
            return this;
        }
        
        public Builder isTransient(boolean isTransient) {
            fieldDefinition.isTransient = isTransient;
            return this;
        }
        
        public Builder isVolatile(boolean isVolatile) {
            fieldDefinition.isVolatile = isVolatile;
            return this;
        }

        public Builder withName(String name) {
            fieldDefinition.name = name;
            return this;
        }

        public Builder withType(String type) {
            fieldDefinition.type = type;
            return this;
        }

        public Builder withVisibility(JavaVisibility visibility) {
            fieldDefinition.visibility = visibility;
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
