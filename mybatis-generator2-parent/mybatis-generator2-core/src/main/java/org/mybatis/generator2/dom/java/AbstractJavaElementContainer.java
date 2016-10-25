package org.mybatis.generator2.dom.java;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public abstract class AbstractJavaElementContainer extends JavaDomNode {
    
    protected List<ClassDefinition> classDefinitions = new ArrayList<>();
    protected List<EnumDefinition> enumDefinitions = new ArrayList<>();
    protected List<InterfaceDefinition> interfaceDefinitions = new ArrayList<>();

    public Stream<ClassDefinition> classDefinitions() {
        return classDefinitions.stream();
    }

    public Stream<EnumDefinition> enumDefinitions() {
        return enumDefinitions.stream();
    }

    public Stream<InterfaceDefinition> interfaceDefinitions() {
        return interfaceDefinitions.stream();
    }
    
    protected abstract static class AbstractJavaElementContainerBuilder<T extends AbstractJavaElementContainerBuilder<T>> {
        
        public T withClassDefinition(ClassDefinition classDefinition) {
            classDefinition.parent = getConcreteItem();
            getConcreteItem().classDefinitions.add(classDefinition);
            return getThis();
        }
        
        public T withClassDefinitions(Stream<ClassDefinition> classDefinitions) {
            classDefinitions.forEach(classDefinition -> {
                classDefinition.parent = getConcreteItem();
                getConcreteItem().classDefinitions.add(classDefinition);
            });
            return getThis();
        }

        public T withEnumDefinition(EnumDefinition enumDefinition) {
            enumDefinition.parent = getConcreteItem();
            getConcreteItem().enumDefinitions.add(enumDefinition);
            return getThis();
        }
        
        public T withEnumDefinitions(Stream<EnumDefinition> enumDefinitions) {
            enumDefinitions.forEach(enumDefinition -> {
                enumDefinition.parent = getConcreteItem();
                getConcreteItem().enumDefinitions.add(enumDefinition);
            });
            return getThis();
        }
        
        public T withInterfaceDefinition(InterfaceDefinition interfaceDefinition) {
            interfaceDefinition.parent = getConcreteItem();
            getConcreteItem().interfaceDefinitions.add(interfaceDefinition);
            return getThis();
        }

        public T withInterfaceDefinitions(Stream<InterfaceDefinition> interfaceDefinitions) {
            interfaceDefinitions.forEach(interfaceDefinition -> {
                interfaceDefinition.parent = getConcreteItem();
                getConcreteItem().interfaceDefinitions.add(interfaceDefinition);
            });
            return getThis();
        }
        
        protected abstract AbstractJavaElementContainer getConcreteItem();
        protected abstract T getThis();
    }
}
