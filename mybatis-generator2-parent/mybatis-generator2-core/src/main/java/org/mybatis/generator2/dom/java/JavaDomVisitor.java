package org.mybatis.generator2.dom.java;

public interface JavaDomVisitor {

    default void visit(MethodDefinition methodDefinition) {
        return;
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
    
    default void visit(EnumConstantDefinition enumConstantDefinition) {
        return;
    }
    
    default boolean visit(CompilationUnit compilationUnit) {
        return true;
    }
    
    default void visit(FieldDefinition fieldDefinition) {
        return;
    }
    
    default void visit(ImportDefinition importDefinition) {
        return;
    }

    default void visit(JavaDoc javaDoc) {
        return;
    }

    default void visit(Parameter parameter) {
        return;
    }

    default void visit(ModifierSet modifierSet) {
        return;
    }

    default void visit(Argument argument) {
        return;
    }

    default void endVisit(ClassDefinition classDefinition) {
        return;
    }

    default void endVisit(EnumDefinition enumDefinition) {
        return;
    }

    default void endVisit(InterfaceDefinition interfaceDefinition) {
        return;
    }
}
