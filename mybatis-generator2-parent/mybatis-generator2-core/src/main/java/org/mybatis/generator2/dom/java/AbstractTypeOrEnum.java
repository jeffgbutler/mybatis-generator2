package org.mybatis.generator2.dom.java;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public abstract class AbstractTypeOrEnum extends AbstractJavaElementContainer {

    private JavaDoc javaDoc;
    private Modifiers modifiers;
    private String name;
    private List<FieldDefinition> fieldDefinitions = new ArrayList<>();
    private List<MethodDefinition> methodDefinitions = new ArrayList<>();
    private List<String> superInterfaces = new ArrayList<>();

    public Optional<JavaDoc> getJavaDoc() {
        return Optional.ofNullable(javaDoc);
    }
    
    public Optional<Modifiers> getModifiers() {
        return Optional.ofNullable(modifiers);
    }
    
    public String getName() {
        return name;
    }
    
    public Stream<FieldDefinition> fields() {
        return fieldDefinitions.stream();
    }

    public Stream<MethodDefinition> methods() {
        return methodDefinitions.stream();
    }

    public Stream<String> superInterfaces() {
        return superInterfaces.stream();
    }
    
    protected abstract static class AbstractTypeOrEnumBuilder<T extends AbstractTypeOrEnumBuilder<T>> extends AbstractJavaElementContainerBuilder<T> {
        
        public T withJavaDoc(JavaDoc javaDoc) {
            javaDoc.parent = getConcreteItem();
            getConcreteItem().javaDoc = javaDoc;
            return getThis();
        }

        public T withModifiers(Modifiers modifiers) {
            modifiers.parent = getConcreteItem();
            getConcreteItem().modifiers = modifiers;
            return getThis();
        }
        
        public T withName(String name) {
            getConcreteItem().name = name;
            return getThis();
        }
        
        public T withField(FieldDefinition field) {
            field.parent = getConcreteItem();
            getConcreteItem().fieldDefinitions.add(field);
            return getThis();
        }
        
        public T withFields(Stream<FieldDefinition> fields) {
            fields.forEach(field -> {
                field.parent = getConcreteItem();
                getConcreteItem().fieldDefinitions.add(field);
            });
            return getThis();
        }

        public T withMethod(MethodDefinition method) {
            method.parent = getConcreteItem();
            getConcreteItem().methodDefinitions.add(method);
            return getThis();
        }

        public T withMethods(Stream<MethodDefinition> methods) {
            methods.forEach(method -> {
                method.parent = getConcreteItem();
                getConcreteItem().methodDefinitions.add(method);
            });
            return getThis();
        }
        
        public T withSuperInterface(String superInterface) {
            getConcreteItem().superInterfaces.add(superInterface);
            return getThis();
        }
        
        public T withSuperInterfaces(Stream<String> superInterfaces) {
            superInterfaces.forEach(getConcreteItem().superInterfaces::add);
            return getThis();
        }
        
        @Override
        protected abstract AbstractTypeOrEnum getConcreteItem();
    }
}
