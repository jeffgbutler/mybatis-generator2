package org.mybatis.generator2.dom.java;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Jeff Butler
 *
 */
public abstract class AbstractMethodDefinition extends JavaDomNode {

    private JavaDoc javaDoc;
    private ModifierSet modifierSet = new ModifierSet(this);
    private List<Parameter> parameters = new ArrayList<>();
    private List<String> bodyLines = new ArrayList<>();
    private List<String> exceptions = new ArrayList<>();
    
    public Optional<JavaDoc> getJavaDoc() {
        return Optional.ofNullable(javaDoc);
    }

    public ModifierSet getModifierSet() {
        return modifierSet;
    }

    public boolean hasExceptions() {
        return !exceptions.isEmpty();
    }
    
    public Stream<String> exceptions() {
        return exceptions.stream();
    }
    
    public Stream<Parameter> parameters() {
        return parameters.stream();
    }

    public Stream<String> bodyLines() {
        return bodyLines.stream();
    }

    protected abstract static class AbstractMethodDefinitionBuilder<T extends AbstractMethodDefinitionBuilder<T>> {
        
        public T withJavaDoc(JavaDoc javaDoc) {
            javaDoc.parent = getMethod();
            getMethod().javaDoc = javaDoc;
            return getThis();
        }

        public T withModifier(JavaModifier javaModifier) {
            getMethod().getModifierSet().addJavaModifier(javaModifier);
            return getThis();
        }

        public T withException(String exception) {
            getMethod().exceptions.add(exception);
            return getThis();
        }
        
        public T withExceptions(Stream<String> exceptions) {
            exceptions.forEach(getMethod().exceptions::add);
            return getThis();
        }
        
        public T withParameter(Parameter parameter) {
            parameter.parent = getMethod();
            getMethod().parameters.add(parameter);
            return getThis();
        }
        
        public T withParameters(Stream<Parameter> parameters) {
            parameters.forEach(parameter -> {
                parameter.parent = getMethod();
                getMethod().parameters.add(parameter);
            });
            return getThis();
        }

        public T withBodyLine(String bodyLine) {
            getMethod().bodyLines.add(bodyLine);
            return getThis();
        }

        public T withBodyLines(Stream<String> bodyLines) {
            bodyLines.forEach(getMethod().bodyLines::add);
            return getThis();
        }

        protected abstract T getThis();
        protected abstract AbstractMethodDefinition getMethod();
    }
}
