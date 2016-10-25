package org.mybatis.generator2.dom.java;

public interface JavaDomVisitor {

    default boolean visit(MethodDefinition methodDefinition) {
        return true;
    }
    
    default boolean visit(InterfaceDefinition interfaceDefinition) {
        return true;
    }
    
    default boolean visit(ClassDefinition classDefinition) {
        return true;
    }
    
    default boolean visit(EnumDefinition enumDefinition) {
        return true;
    }
    
    default boolean visit(EnumConstantDefinition enumConstantDefinition) {
        return true;
    }
    
    default boolean visit(CompilationUnit compilationUnit) {
        return true;
    }
    
    default boolean visit(FieldDefinition fieldDefinition) {
        return true;
    }
    
    default boolean visit(ImportDefinition importDefinition) {
        return true;
    }

    default boolean visit(JavaDoc javaDoc) {
        return true;
    }

    default boolean visit(Parameter parameter) {
        return true;
    }
}
