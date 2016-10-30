package org.mybatis.generator2.dom.java;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mybatis.generator2.dom.java.JavaDomNode.JavaNodeType;

public class JavaDocTest {

    @Test
    public void testWithPregeneratedJavadoc() {
        List<String> javaDocLines = new ArrayList<>();
        javaDocLines.add("/**");
        javaDocLines.add(" * Test line");
        javaDocLines.add(" */");
        
        JavaDoc javaDoc = JavaDoc.of(javaDocLines.stream());
        assertThat(javaDoc.javaDocLines().count(), is(3l));
    }

    @Test
    public void testWithPostgeneratedJavadoc() {
        JavaDoc javaDoc = new JavaDoc.Builder()
                .withJavaDocLine("/**")
                .withJavaDocLine(" * Test line")
                .withJavaDocLine(" */")
                .build();
        
        assertThat(javaDoc.getNodeType(), is(JavaNodeType.JAVADOC));
        assertThat(javaDoc.allowsModifier(JavaModifier.PUBLIC), is(false));
        assertThat(javaDoc.javaDocLines().count(), is(3l));
    }
}
