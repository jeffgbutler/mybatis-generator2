package org.mybatis.generator2.dom.java;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class CompilationUnit extends AbstractJavaElementContainer {

    private String _package;
    private Set<ImportDefinition> importDefinitions = new HashSet<>();
    
    private CompilationUnit() {
        super();
    }
    
    public Stream<ImportDefinition> staticImports() {
        return importDefinitions.stream().filter(i -> i.isStatic()).sorted();
    }

    public Stream<ImportDefinition> nonStaticImports() {
        return importDefinitions.stream().filter(i -> !i.isStatic()).sorted();
    }
    
    public String getPackage() {
        return _package;
    }

    @Override
    public void accept(JavaDomVisitor visitor) {
        if (visitor.visit(this)) {
            importDefinitions.stream().forEach(i -> i.accept(visitor));
            classDefinitions.stream().forEach(t -> t.accept(visitor));
            enumDefinitions.stream().forEach(t -> t.accept(visitor));
            interfaceDefinitions.stream().forEach(t -> t.accept(visitor));
        }
    }
    
    public static class Builder extends AbstractJavaElementContainerBuilder<Builder> {
        private CompilationUnit compilationUnit = new CompilationUnit();
        
        public Builder withPackage(String _package) {
            compilationUnit._package = _package;
            return this;
        }
        
        public Builder withImport(ImportDefinition _import) {
            _import.parent = compilationUnit;
            compilationUnit.importDefinitions.add(_import);
            return this;
        }

        public Builder withImports(Stream<ImportDefinition> imports) {
            imports.forEach(_import -> {
                _import.parent = compilationUnit;
                compilationUnit.importDefinitions.add(_import);
            });
            return this;
        }

        @Override
        protected CompilationUnit getConcreteItem() {
            return compilationUnit;
        }

        @Override
        protected Builder getThis() {
            return this;
        }
        
        public CompilationUnit build() {
            return getConcreteItem();
        }
    }
}