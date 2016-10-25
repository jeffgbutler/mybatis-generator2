package org.mybatis.generator2.dom.java;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class JavaDoc extends JavaDomNode {

    private List<String> javaDocLines = new ArrayList<>();
    
    private JavaDoc() {
        super();
    }

    @Override
    public void accept(JavaDomVisitor visitor) {
        visitor.visit(this);
    }

    public Stream<String> javaDocLines() {
        return javaDocLines.stream();
    }
    
    public JavaDoc withJavaDocLine(String javaDocLine) {
        return new Builder()
                .withJavaDocLines(javaDocLines())
                .withJavaDocLine(javaDocLine)
                .build();
    }
    
    public JavaDoc withJavaDocLines(Stream<String> javaDocLines) {
        return new Builder()
                .withJavaDocLines(javaDocLines())
                .withJavaDocLines(javaDocLines)
                .build();
    }
    
    public static JavaDoc of(Stream<String> javaDocLines) {
        return new Builder()
                .withJavaDocLines(javaDocLines)
                .build();
    }
    
    public static class Builder {
        private JavaDoc javaDoc = new JavaDoc();
        
        public Builder withJavaDocLines(Stream<String> javaDocLines) {
            javaDocLines.forEach(javaDoc.javaDocLines::add);
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
