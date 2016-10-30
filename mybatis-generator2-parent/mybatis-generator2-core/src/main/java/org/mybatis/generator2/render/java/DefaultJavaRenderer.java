package org.mybatis.generator2.render.java;

import java.util.stream.Collectors;

import org.mybatis.generator2.dom.java.AbstractMethodDefinition;
import org.mybatis.generator2.dom.java.AbstractTypeOrEnum;
import org.mybatis.generator2.dom.java.Argument;
import org.mybatis.generator2.dom.java.ClassDefinition;
import org.mybatis.generator2.dom.java.CompilationUnit;
import org.mybatis.generator2.dom.java.ConstructorDefinition;
import org.mybatis.generator2.dom.java.EnumConstantDefinition;
import org.mybatis.generator2.dom.java.EnumDefinition;
import org.mybatis.generator2.dom.java.FieldDefinition;
import org.mybatis.generator2.dom.java.ImportDefinition;
import org.mybatis.generator2.dom.java.InterfaceDefinition;
import org.mybatis.generator2.dom.java.JavaDoc;
import org.mybatis.generator2.dom.java.JavaDomNode.JavaNodeType;
import org.mybatis.generator2.dom.java.JavaDomVisitor;
import org.mybatis.generator2.dom.java.MethodDefinition;
import org.mybatis.generator2.dom.java.ModifierSet;
import org.mybatis.generator2.dom.java.Parameter;
import org.mybatis.generator2.render.OutputUtilities;
import org.mybatis.generator2.util.StringUtils;

public class DefaultJavaRenderer {

    private CompilationUnit compilationUnit;
    
    private DefaultJavaRenderer() {
        super();
    }

    public String render() {
        return new Renderer(compilationUnit).render();
    }
    
    public static DefaultJavaRenderer of(CompilationUnit compilationUnit) {
        DefaultJavaRenderer defaultJavaRenderer = new DefaultJavaRenderer();
        defaultJavaRenderer.compilationUnit = compilationUnit;
        return defaultJavaRenderer;
    }
    
    private static class Renderer implements JavaDomVisitor, OutputUtilities {
        private StringBuilder buffer = new StringBuilder();
        private int indentLevel = 0;
        private CompilationUnit compilationUnit;
        
        public Renderer(CompilationUnit compilationUnit) {
            this.compilationUnit = compilationUnit;
        }

        public String render() {
            compilationUnit.accept(this);
            return buffer.toString();
        }
        
        @Override
        public boolean visit(CompilationUnit compilationUnit) {
            compilationUnit.getPackage().ifPresent(p -> {
                buffer.append("package ");
                buffer.append(p);
                buffer.append(';');
                newLine(buffer);
            });
            
            if (compilationUnit.staticImports().count() > 0) {
                newLine(buffer);
            }
            
            compilationUnit.staticImports().forEach(i -> i.accept(this));
            
            if (compilationUnit.nonStaticImports().count() > 0) {
                newLine(buffer);
            }

            compilationUnit.nonStaticImports().forEach(i -> i.accept(this));
            
            return true;
        }

        @Override
        public void visit(ImportDefinition importDefinition) {
            buffer.append(importDefinition.toString());
            newLine(buffer);
        }
        
        @Override
        public void visit(JavaDoc javaDoc) {
            javaDoc.javaDocLines().forEach(l -> {
                javaIndent(buffer, indentLevel);
                buffer.append(l);
                newLine(buffer);
            });
        }
        
        @Override
        public boolean visit(ClassDefinition classDefinition) {
            newLine(buffer);
            classDefinition.getJavaDoc().ifPresent(j -> j.accept(this));
            javaIndent(buffer, indentLevel);
            classDefinition.getModifierSet().accept(this);
            buffer.append("class ");
            buffer.append(classDefinition.getName());
            buffer.append(' ');
            
            classDefinition.getSuperClass().ifPresent(s -> {
                buffer.append("extends ");
                buffer.append(s);
                buffer.append(' ');
            });
            
            addSuperInterfaces(classDefinition);
                
            buffer.append("{");
            newLine(buffer);
            indentLevel++;
            return true;
        }
        
        @Override
        public void endVisit(ClassDefinition classDefinition) {
            indentLevel--;
            javaIndent(buffer, indentLevel);
            buffer.append('}');
            newLine(buffer);
        }
        
        @Override
        public boolean visit(EnumDefinition enumDefinition) {
            newLine(buffer);
            enumDefinition.getJavaDoc().ifPresent(j -> j.accept(this));
            javaIndent(buffer, indentLevel);
            enumDefinition.getModifierSet().accept(this);
            buffer.append("enum ");
            buffer.append(enumDefinition.getName());
            buffer.append(' ');
            
            addSuperInterfaces(enumDefinition);

            buffer.append("{");
            newLine(buffer);
            indentLevel++;

            javaIndent(buffer, indentLevel);
            enumDefinition.enumConstants().findFirst().ifPresent(c -> c.accept(this));
            
            enumDefinition.enumConstants().skip(1).forEach(c -> {
                buffer.append(',');
                newLine(buffer);
                javaIndent(buffer, indentLevel);
                c.accept(this);
            });
            buffer.append(';');
            newLine(buffer);
            
            if (enumDefinition.hasFields()) {
                newLine(buffer);
            }
            
            return true;
        }
        
        @Override
        public void endVisit(EnumDefinition enumDefinition) {
            indentLevel--;
            javaIndent(buffer, indentLevel);
            buffer.append('}');
            newLine(buffer);
        }

        @Override
        public boolean visit(InterfaceDefinition interfaceDefinition) {
            newLine(buffer);
            interfaceDefinition.getJavaDoc().ifPresent(j -> j.accept(this));
            javaIndent(buffer, indentLevel);
            interfaceDefinition.getModifierSet().accept(this);
            buffer.append("interface ");
            buffer.append(interfaceDefinition.getName());
            buffer.append(' ');
            
            addSuperInterfaces(interfaceDefinition);
                
            buffer.append("{");
            newLine(buffer);
            indentLevel++;
            return true;
        }
        
        @Override
        public void endVisit(InterfaceDefinition interfaceDefinition) {
            indentLevel--;
            javaIndent(buffer, indentLevel);
            buffer.append('}');
            newLine(buffer);
        }

        private void addSuperInterfaces(AbstractTypeOrEnum<?> type) {
            String prefix = type.getNodeType() == JavaNodeType.INTERFACE ? "extends " : "implements ";
            if (type.hasSuperInterfaces()) {
                buffer.append(type.superInterfaces()
                        .collect(Collectors.joining(", ", prefix, " ")));
            };
        }
        
        @Override
        public void visit(EnumConstantDefinition enumConstantDefinition) {
            buffer.append(enumConstantDefinition.getName());
            
            if (enumConstantDefinition.hasArguments()) {
                buffer.append('(');
                enumConstantDefinition.arguments().limit(1).forEach(a -> a.accept(this));
                enumConstantDefinition.arguments().skip(1).forEach(a -> {
                    buffer.append(", ");
                    a.accept(this);
                });
                buffer.append(')');
            }
        }
        
        @Override
        public void visit(Argument argument) {
            if (argument.isString()) {
                buffer.append(String.format("\"%s\"", argument.getValue()));
            } else {
                buffer.append(argument.getValue());
            }
        }
        
        @Override
        public void visit(ModifierSet modifierSet) {
            modifierSet.javaModifiers()
                .filter(m -> m.isApplicable(modifierSet.getParent()))
                .forEach(m -> {
                    buffer.append(m.getKeyword());
                    buffer.append(' ');
                });
        }
        
        @Override
        public void visit(FieldDefinition fieldDefinition) {
            fieldDefinition.getJavaDoc().ifPresent(j -> j.accept(this));
            javaIndent(buffer, indentLevel);
            fieldDefinition.getModifierSet().accept(this);
            buffer.append(fieldDefinition.getType());
            buffer.append(' ');
            buffer.append(fieldDefinition.getName());
            
            fieldDefinition.getInitializationString().ifPresent(i -> {
                buffer.append(" = ");
                buffer.append(i);
            });
            buffer.append(';');
            newLine(buffer);
        }
        
        @Override
        public void visit(MethodDefinition methodDefinition) {
            newLine(buffer);
            methodDefinition.getJavaDoc().ifPresent(j -> j.accept(this));
            javaIndent(buffer, indentLevel);
            methodDefinition.getModifierSet().accept(this);
            methodDefinition.getReturnType().ifPresent(t -> {
                buffer.append(t);
                buffer.append(' ');
            });
            buffer.append(methodDefinition.getName());
            
            addMethodParameters(methodDefinition);
            addMethodExceptions(methodDefinition);

            if (methodDefinition.allowsBodyLines()) {
                addMethodBody(methodDefinition);
            } else {
                buffer.append(';');
                newLine(buffer);
            }
        }
        
        @Override
        public void visit(ConstructorDefinition constructorDefinition) {
            newLine(buffer);
            constructorDefinition.getJavaDoc().ifPresent(j -> j.accept(this));
            javaIndent(buffer, indentLevel);
            constructorDefinition.getModifierSet().accept(this);
            buffer.append(((AbstractTypeOrEnum<?>) constructorDefinition.getParent()).getName());
            
            addMethodParameters(constructorDefinition);
            addMethodExceptions(constructorDefinition);
            addMethodBody(constructorDefinition);
        }

        private void addMethodParameters(AbstractMethodDefinition<?> method) {
            buffer.append('(');
            method.parameters().limit(1).forEach(p -> p.accept(this));
            method.parameters().skip(1).forEach(p -> {
                buffer.append(", ");
                p.accept(this);
            });
            buffer.append(")");
        }
        
        private void addMethodExceptions(AbstractMethodDefinition<?> method) {
            if (method.hasExceptions()) {
                buffer.append(method.exceptions()
                        .collect(Collectors.joining(", ", " throws ", "")));
            }
        }
        
        private void addMethodBody(AbstractMethodDefinition<?> method) {
            buffer.append(" {");
            newLine(buffer);
            
            indentLevel++;
            method.bodyLines().forEach(this::handleBodyLine);
            indentLevel--;
            
            javaIndent(buffer, indentLevel);
            buffer.append('}');
            newLine(buffer);
        }
        
        private void handleBodyLine(String bodyline) {
            if (bodyline.startsWith("}")) {
                indentLevel--;
                writeBodyLine(bodyline);
            } else if (bodyline.startsWith("case") || bodyline.startsWith("default")) {
                indentLevel--;
                writeBodyLine(bodyline);
                indentLevel++;
            } else {
                writeBodyLine(bodyline);
            }
            
            if (bodyline.endsWith("{")) { //$NON-NLS-1$
                indentLevel++;
            }
        }
        
        private void writeBodyLine(String bodyline) {
            if (StringUtils.stringHasValue(bodyline)) {
                javaIndent(buffer, indentLevel);
                buffer.append(bodyline);
                newLine(buffer);
            } else {
                // for an empty line, don't write the spaces from the indent - just new line
                newLine(buffer);
            }
        }

        @Override
        public void visit(Parameter parameter) {
            parameter.getModifierSet().accept(this);
            buffer.append(parameter.getType());
            buffer.append(' ');
            buffer.append(parameter.getName());
        }
    }
}
