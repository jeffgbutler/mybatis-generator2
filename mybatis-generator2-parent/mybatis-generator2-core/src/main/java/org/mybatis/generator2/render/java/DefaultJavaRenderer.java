package org.mybatis.generator2.render.java;

import java.util.stream.Collectors;

import org.mybatis.generator2.dom.java.ClassDefinition;
import org.mybatis.generator2.dom.java.CompilationUnit;
import org.mybatis.generator2.dom.java.FieldDefinition;
import org.mybatis.generator2.dom.java.JavaDoc;
import org.mybatis.generator2.dom.java.JavaDomVisitor;
import org.mybatis.generator2.dom.java.MethodDefinition;
import org.mybatis.generator2.dom.java.Modifiers;
import org.mybatis.generator2.dom.java.Parameter;
import org.mybatis.generator2.render.OutputUtilities;

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
            
            compilationUnit.staticImports().forEach(i -> {
                buffer.append(i.toString());
                newLine(buffer);
            });
            
            if (compilationUnit.nonStaticImports().count() > 0) {
                newLine(buffer);
            }

            compilationUnit.nonStaticImports().forEach(i -> {
                buffer.append(i.toString());
                newLine(buffer);
            });
            
            return true;
        }
        
        @Override
        public boolean visit(JavaDoc javaDoc) {
            javaDoc.javaDocLines().forEach(l -> {
                javaIndent(buffer, indentLevel);
                buffer.append(l);
                newLine(buffer);
            });
            return true;
        }
        
        @Override
        public boolean visit(ClassDefinition classDefinition) {
            newLine(buffer);
            classDefinition.getJavaDoc().ifPresent(j -> j.accept(this));
            javaIndent(buffer, indentLevel);
            classDefinition.getModifiers().ifPresent(m -> m.accept(this));
            buffer.append("class ");
            buffer.append(classDefinition.getName());
            
            classDefinition.getSuperClass().ifPresent(s -> {
                buffer.append("extends ");
                buffer.append(s);
                buffer.append(' ');
            });
            
            if (classDefinition.hasSuperInterfaces()) {
                buffer.append("implements ");
                buffer.append(classDefinition.superInterfaces()
                        .collect(Collectors.joining(", ")));
                buffer.append(' ');
            };
                
            buffer.append(" {");
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
        public boolean visit(Modifiers modifiers) {
            modifiers.javaModifiers()
                .filter(m -> m.isApplicable(modifiers.getParent()))
                .forEach(m -> {
                    buffer.append(m.getKeyword());
                    buffer.append(' ');
                });
            return true;
        }
        
        @Override
        public boolean visit(FieldDefinition fieldDefinition) {
            fieldDefinition.getJavaDoc().ifPresent(j -> j.accept(this));
            javaIndent(buffer, indentLevel);
            fieldDefinition.getModifiers().ifPresent(m -> m.accept(this));
            buffer.append(fieldDefinition.getType());
            buffer.append(' ');
            buffer.append(fieldDefinition.getName());
            
            fieldDefinition.getInitializationString().ifPresent(i -> {
                buffer.append(" = ");
                buffer.append(i);
            });
            buffer.append(';');
            newLine(buffer);
            
            return true;
        }
        
        @Override
        public boolean visit(MethodDefinition methodDefinition) {
            newLine(buffer);
            methodDefinition.getJavaDoc().ifPresent(j -> j.accept(this));
            javaIndent(buffer, indentLevel);
            methodDefinition.getModifiers().ifPresent(m -> m.accept(this));
            methodDefinition.getReturnType().ifPresent(t -> {
                buffer.append(t);
                buffer.append(' ');
            });
            buffer.append(methodDefinition.getName());
            buffer.append('(');
            
            methodDefinition.parameters().limit(1).forEach(p -> p.accept(this));
            methodDefinition.parameters().skip(1).forEach(p -> {
                buffer.append(", ");
                p.accept(this);
            });
            
            buffer.append(") ");
            
            if (methodDefinition.hasExceptions()) {
                buffer.append("throws ");
                buffer.append(methodDefinition.exceptions()
                        .collect(Collectors.joining(", ")));
            }
            
            if (methodDefinition.allowsBodyLines()) {
                buffer.append('{');
                newLine(buffer);
            
                indentLevel++;
                methodDefinition.bodyLines().forEach(this::handleBodyLine);
                indentLevel--;
            
                javaIndent(buffer, indentLevel);
                buffer.append('}');
            } else {
                buffer.append(';');
            }
            newLine(buffer);
            
            return true;
        }
        
        @Override
        public boolean visit(Parameter parameter) {
            parameter.getModifiers().ifPresent(m -> m.accept(this));
            buffer.append(parameter.getType());
            buffer.append(' ');
            buffer.append(parameter.getName());
            return true;
        }
        
        private void handleBodyLine(String bodyline) {
            // TODO - handle blocks and switch statements
            javaIndent(buffer, indentLevel);
            buffer.append(bodyline);
            newLine(buffer);
        }
    }
}
