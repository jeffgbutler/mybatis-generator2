package org.mybatis.generator2.dom.java;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class MethodDefinition extends JavaDomNode {

    private JavaDoc javaDoc;
    private Modifiers modifiers;
    private String returnType;
    private String name;
    private List<Parameter> parameters = new ArrayList<>();
    private List<String> bodyLines = new ArrayList<>();
    
    private MethodDefinition() {
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

    public Optional<String> getReturnType() {
        return Optional.ofNullable(returnType);
    }

    public boolean isConstructor() {
        return returnType == null;
    }
    
    public String getName() {
        return name;
    }

    public Stream<Parameter> parameters() {
        return parameters.stream();
    }

    public Stream<String> bodyLines() {
        return bodyLines.stream();
    }

    public static class Builder {
        private MethodDefinition methodDefinition = new MethodDefinition();
        
        public Builder(String name) {
            methodDefinition.name = name;
        }
        
        public Builder withJavaDoc(JavaDoc javaDoc) {
            javaDoc.parent = methodDefinition;
            methodDefinition.javaDoc = javaDoc;
            return this;
        }
        
        public Builder withModifiers(Modifiers modifiers) {
            modifiers.parent = methodDefinition;
            methodDefinition.modifiers = modifiers;
            return this;
        }
        
        public Builder withReturnType(String returnType) {
            methodDefinition.returnType = returnType;
            return this;
        }
        
        public Builder withParameter(Parameter parameter) {
            parameter.parent = methodDefinition;
            methodDefinition.parameters.add(parameter);
            return this;
        }
        
        public Builder withParameters(Stream<Parameter> parameters) {
            parameters.forEach(parameter -> {
                parameter.parent = methodDefinition;
                methodDefinition.parameters.add(parameter);
            });
            return this;
        }

        public Builder withBodyLine(String bodyLine) {
            methodDefinition.bodyLines.add(bodyLine);
            return this;
        }

        public Builder withBodyLines(Stream<String> bodyLines) {
            bodyLines.forEach(methodDefinition.bodyLines::add);
            return this;
        }
        
        public MethodDefinition build() {
            return methodDefinition;
        }
    }
}
