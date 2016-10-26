package org.mybatis.generator2.render.java;

import static org.mybatis.generator2.util.StringUtils.stringHasValue;

import org.mybatis.generator2.dom.java.ClassDefinition;
import org.mybatis.generator2.dom.java.CompilationUnit;
import org.mybatis.generator2.dom.java.JavaDoc;
import org.mybatis.generator2.dom.java.JavaDomVisitor;
import org.mybatis.generator2.dom.java.Modifiers;
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
            classDefinition.getJavaDoc().ifPresent(j -> j.accept(this));
            classDefinition.getModifiers().ifPresent(m -> m.accept(this));
            buffer.append("class ");
            buffer.append(classDefinition.getName());
            buffer.append(" {");
            indentLevel++;
            return true;
        }
        
        @Override
        public void endVisit(ClassDefinition classDefinition) {
            indentLevel--;
            newLine(buffer);
            buffer.append('}');
        }
        
        @Override
        public boolean visit(Modifiers modifiers) {
            javaIndent(buffer, indentLevel);
            modifiers.modifiers().forEach(m -> {
                buffer.append(m.getKeyword());
                buffer.append(' ');
            });
            return true;
        }
    }
}
