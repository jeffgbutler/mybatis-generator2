package org.mybatis.generator2.dom.java;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * If a method has a null return type, it is considered to be a constructor.
 * If a regular method does not have a return type, then set the return type to "void".
 * 
 * @author Jeff Butler
 *
 */
public class MethodDefinition extends JavaDomNode {

    private JavaDoc javaDoc;
    private Modifiers modifiers;
    private String returnType;
    private String name;
    private List<Parameter> parameters = new ArrayList<>();
    private List<String> bodyLines = new ArrayList<>();
    private List<String> exceptions = new ArrayList<>();
    
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
            rc = true;
            break;
            
        case ABSTRACT:
        case STATIC:
        case FINAL:
        case SYNCHRONIZED:
        case NATIVE:
        case STRICTFP:
            rc = !isConstructor();
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
    
    public boolean hasExceptions() {
        return !exceptions.isEmpty();
    }
    
    public Stream<String> exceptions() {
        return exceptions.stream();
    }
    
    public String getName() {
        return name;
    }

    public Stream<Parameter> parameters() {
        return parameters.stream();
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
        if (modifiers == null) {
            return true;
        }
        
        return !(modifiers.isAbstract() || modifiers.isNative());
    }

    private boolean isBodyAllowedInInterface() {
        if (modifiers == null) {
            return false;
        }
        
        return modifiers.isDefault() || modifiers.isStatic();
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
        
        public Builder withException(String exception) {
            methodDefinition.exceptions.add(exception);
            return this;
        }
        
        public Builder withExceptions(Stream<String> exceptions) {
            exceptions.forEach(methodDefinition.exceptions::add);
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
