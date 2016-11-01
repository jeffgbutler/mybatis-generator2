package org.mybatis.generator2.dom.java;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class JavaDoc extends JavaDomNode<JavaDoc> {

    private List<String> javaDocLines = new ArrayList<>();
    
    private JavaDoc() {
        super();
    }

    @Override
    public void accept(JavaDomVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public JavaNodeType getNodeType() {
        return JavaNodeType.JAVADOC;
    }
    
    @Override
    public boolean allowsModifier(JavaModifier javaModifier) {
        return false;
    }
    
    @Override
    public JavaDoc deepCopy() {
        return new Builder()
                .withJavaDocLines(javaDocLines())
                .build();
    }

    public Stream<String> javaDocLines() {
        return javaDocLines.stream();
    }
    
    public static JavaDoc of(Stream<String> javaDocLines) {
        return new Builder()
                .withJavaDocLines(javaDocLines)
                .build();
    }
    
    public static class Builder {
        private JavaDoc javaDoc = new JavaDoc();
        
        public Builder withJavaDocLines(Stream<String> javaDocLines) {
            javaDocLines.forEach(this::withJavaDocLine);
            return this;
        }
        
        public Builder withJavaDocLine(String javaDocLine) {
            javaDoc.javaDocLines.add(javaDocLine);
            return this;
        }
        
        public JavaDoc build() {
            return javaDoc;
        }
    }
}
