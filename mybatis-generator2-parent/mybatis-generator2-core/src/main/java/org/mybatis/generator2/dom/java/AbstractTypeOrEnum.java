package org.mybatis.generator2.dom.java;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public abstract class AbstractTypeOrEnum<T> extends AbstractJavaElementContainer<T> {

    protected JavaDoc javaDoc;
    protected Set<JavaModifier> modifiers = new HashSet<>();
    String name;
    private List<FieldDefinition> fieldDefinitions = new ArrayList<>();
    private List<MethodDefinition> methodDefinitions = new ArrayList<>();
    private List<String> superInterfaces = new ArrayList<>();

    public Optional<JavaDoc> getJavaDoc() {
        return Optional.ofNullable(javaDoc);
    }
    
    public ModifierSet getModifierSet() {
        return new ModifierSet(this, modifiers);
    }
    
    public String getName() {
        return name;
    }
    
    public Stream<FieldDefinition> fields() {
        return fieldDefinitions.stream();
    }
    
    public boolean hasFields() {
        return !fieldDefinitions.isEmpty();
    }

    public Stream<MethodDefinition> methods() {
        return methodDefinitions.stream();
    }

    public boolean hasMethods() {
        return !methodDefinitions.isEmpty();
    }

    public Stream<String> superInterfaces() {
        return superInterfaces.stream();
    }
    
    public boolean hasSuperInterfaces() {
        return !superInterfaces.isEmpty();
    }
    
    protected void acceptChildren(JavaDomVisitor visitor) {
        fields().forEach(f -> f.accept(visitor));
        acceptConstructors(visitor);
        methods().forEach(m -> m.accept(visitor));
        classes().forEach(t -> t.accept(visitor));
        enums().forEach(t -> t.accept(visitor));
        interfaces().forEach(t -> t.accept(visitor));
    }
    
    protected abstract void acceptConstructors(JavaDomVisitor visitor);
    
    protected abstract static class AbstractTypeOrEnumBuilder<T extends AbstractTypeOrEnumBuilder<T, S>, S extends AbstractTypeOrEnum<S>> extends AbstractJavaElementContainerBuilder<T, S> {
        
        public T withJavaDoc(JavaDoc javaDoc) {
            if (javaDoc != null) {
                javaDoc.parent = getConcreteItem();
            }
            getConcreteItem().javaDoc = javaDoc;
            return getThis();
        }

        public T withModifier(JavaModifier javaModifier) {
            getConcreteItem().modifiers.add(javaModifier);
            return getThis();
        }
        
        public T withModifiers(Stream<JavaModifier> javaModifiers) {
            javaModifiers.forEach(this::withModifier);
            return getThis();
        }
        
        public T withField(FieldDefinition field) {
            field.parent = getConcreteItem();
            getConcreteItem().fieldDefinitions.add(field);
            return getThis();
        }
        
        public T withFields(Stream<FieldDefinition> fields) {
            fields.forEach(this::withField);
            return getThis();
        }

        public T withMethod(MethodDefinition method) {
            method.parent = getConcreteItem();
            getConcreteItem().methodDefinitions.add(method);
            return getThis();
        }

        public T withMethods(Stream<MethodDefinition> methods) {
            methods.forEach(this::withMethod);
            return getThis();
        }
        
        public T withSuperInterface(String superInterface) {
            getConcreteItem().superInterfaces.add(superInterface);
            return getThis();
        }
        
        public T withSuperInterfaces(Stream<String> superInterfaces) {
            superInterfaces.forEach(this::withSuperInterface);
            return getThis();
        }
        
        @Override
        protected abstract AbstractTypeOrEnum<S> getConcreteItem();
    }
}
