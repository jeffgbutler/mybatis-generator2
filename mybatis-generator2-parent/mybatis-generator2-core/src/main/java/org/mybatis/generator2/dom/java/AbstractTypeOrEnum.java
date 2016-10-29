package org.mybatis.generator2.dom.java;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public abstract class AbstractTypeOrEnum extends AbstractJavaElementContainer {

    private JavaDoc javaDoc;
    private ModifierSet modifierSet = new ModifierSet(this);
    String name;
    private List<FieldDefinition> fieldDefinitions = new ArrayList<>();
    private List<MethodDefinition> methodDefinitions = new ArrayList<>();
    private List<String> superInterfaces = new ArrayList<>();

    public Optional<JavaDoc> getJavaDoc() {
        return Optional.ofNullable(javaDoc);
    }
    
    public ModifierSet getModifierSet() {
        return modifierSet;
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
    
    protected abstract static class AbstractTypeOrEnumBuilder<T extends AbstractTypeOrEnumBuilder<T>> extends AbstractJavaElementContainerBuilder<T> {
        
        public T withJavaDoc(JavaDoc javaDoc) {
            javaDoc.parent = getConcreteItem();
            getConcreteItem().javaDoc = javaDoc;
            return getThis();
        }

        public T withModifier(JavaModifier javaModifier) {
            getConcreteItem().modifierSet.addJavaModifier(javaModifier);
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
