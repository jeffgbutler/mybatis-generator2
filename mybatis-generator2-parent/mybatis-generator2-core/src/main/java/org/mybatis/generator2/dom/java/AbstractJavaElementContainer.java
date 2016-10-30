package org.mybatis.generator2.dom.java;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public abstract class AbstractJavaElementContainer extends JavaDomNode {

    private List<ClassDefinition> classDefinitions = new ArrayList<>();
    private List<EnumDefinition> enumDefinitions = new ArrayList<>();
    private List<InterfaceDefinition> interfaceDefinitions = new ArrayList<>();

    public Stream<ClassDefinition> classes() {
        return classDefinitions.stream();
    }

    public Stream<EnumDefinition> enums() {
        return enumDefinitions.stream();
    }

    public Stream<InterfaceDefinition> interfaces() {
        return interfaceDefinitions.stream();
    }
    
    protected abstract static class AbstractJavaElementContainerBuilder<T extends AbstractJavaElementContainerBuilder<T>> {
        
        public T withClassDefinition(ClassDefinition classDefinition) {
            classDefinition.parent = getConcreteItem();
            getConcreteItem().classDefinitions.add(classDefinition);
            return getThis();
        }
        
        public T withEnumDefinition(EnumDefinition enumDefinition) {
            enumDefinition.parent = getConcreteItem();
            getConcreteItem().enumDefinitions.add(enumDefinition);
            return getThis();
        }
        
        public T withInterfaceDefinition(InterfaceDefinition interfaceDefinition) {
            interfaceDefinition.parent = getConcreteItem();
            getConcreteItem().interfaceDefinitions.add(interfaceDefinition);
            return getThis();
        }

        protected abstract AbstractJavaElementContainer getConcreteItem();
        protected abstract T getThis();
    }
}
