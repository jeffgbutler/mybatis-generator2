package org.mybatis.generator2.dom.java;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public class CompilationUnit extends AbstractJavaElementContainer {

    private String pakkage;
    private Set<ImportDefinition> importDefinitions = new HashSet<>();
    
    private CompilationUnit() {
        super();
    }
    
    public Stream<ImportDefinition> staticImports() {
        return importDefinitions.stream().filter(ImportDefinition::isStatic).sorted();
    }

    public Stream<ImportDefinition> nonStaticImports() {
        return importDefinitions.stream().filter(ImportDefinition::isNonStatic).sorted();
    }
    
    public Optional<String> getPackage() {
        return Optional.ofNullable(pakkage);
    }

    @Override
    public void accept(JavaDomVisitor visitor) {
        if (visitor.visit(this)) {
            classes().forEach(t -> t.accept(visitor));
            enums().forEach(t -> t.accept(visitor));
            interfaces().forEach(t -> t.accept(visitor));
        }
    }
    
    @Override
    public JavaNodeType getNodeType() {
        return JavaNodeType.COMPILATION_UNIT;
    }
    
    @Override
    public boolean allowsModifier(JavaModifier javaModifier) {
        return false;
    }

    public static class Builder extends AbstractJavaElementContainerBuilder<Builder> {
        private CompilationUnit compilationUnit = new CompilationUnit();
        
        public Builder inPackage(String pakkage) {
            compilationUnit.pakkage = pakkage;
            return this;
        }
        
        /**
         * Convenience method - create a non static, non-wildcard import
         * 
         * @param importDefinition
         * @return
         */
        public Builder withImport(String importDefinition) {
            ImportDefinition id = ImportDefinition.of(importDefinition);
            id.parent = compilationUnit;
            compilationUnit.importDefinitions.add(id);
            return this;
        }

        public Builder withImport(ImportDefinition importDefinition) {
            importDefinition.parent = compilationUnit;
            compilationUnit.importDefinitions.add(importDefinition);
            return this;
        }

        public Builder withImports(Stream<ImportDefinition> imports) {
            imports.forEach(importDefinition -> {
                importDefinition.parent = compilationUnit;
                compilationUnit.importDefinitions.add(importDefinition);
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
