package org.mybatis.generator2.dom.java;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public class CompilationUnit extends AbstractJavaElementContainer<CompilationUnit> {

    private String pakkage;
    private Set<ImportDefinition> importDefinitions = new HashSet<>();
    private JavaDoc javaDoc;
    
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
    
    public Optional<JavaDoc> getJavaDoc() {
        return Optional.ofNullable(javaDoc);
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
    
    @Override
    public CompilationUnit deepCopy() {
        return new Builder()
                .inPackage(pakkage)
                .withJavaDoc(javaDoc == null ? null : javaDoc.deepCopy())
                .withImports(importDefinitions.stream().map(ImportDefinition::deepCopy))
                .withClassDefinitions(classes().map(ClassDefinition::deepCopy))
                .withEnumDefinitions(enums().map(EnumDefinition::deepCopy))
                .withInterfaceDefinitions(interfaces().map(InterfaceDefinition::deepCopy))
                .build();
    }

    public static class Builder extends AbstractJavaElementContainerBuilder<Builder, CompilationUnit> {
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
            return withImport(id);
        }

        public Builder withImport(ImportDefinition importDefinition) {
            importDefinition.parent = compilationUnit;
            compilationUnit.importDefinitions.add(importDefinition);
            return this;
        }

        public Builder withImports(Stream<ImportDefinition> imports) {
            imports.forEach(this::withImport);
            return this;
        }
        
        public Builder withJavaDoc(JavaDoc javaDoc) {
            if (javaDoc != null) {
                javaDoc.parent = compilationUnit;
            }
            compilationUnit.javaDoc = javaDoc;
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
