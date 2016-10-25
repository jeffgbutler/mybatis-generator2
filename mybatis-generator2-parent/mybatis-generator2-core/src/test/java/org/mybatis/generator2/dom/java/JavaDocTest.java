package org.mybatis.generator2.dom.java;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

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
        List<String> javaDocLines = new ArrayList<>();
        javaDocLines.add("/**");
        javaDocLines.add(" * Test line");
        javaDocLines.add(" */");
        
        List<String> emptyList = Collections.emptyList();
        JavaDoc javaDoc = JavaDoc.of(emptyList.stream());
        javaDoc = javaDoc.withJavaDocLines(javaDocLines.stream());
        assertThat(javaDoc.javaDocLines().count(), is(3l));
    }

    @Test
    public void testWithPostgeneratedJavadocOneAtATime() {
        List<String> emptyList = Collections.emptyList();
        JavaDoc javaDoc = JavaDoc.of(emptyList.stream());
        javaDoc = javaDoc.withJavaDocLine("/**");
        javaDoc = javaDoc.withJavaDocLine(" * Test line");
        javaDoc = javaDoc.withJavaDocLine(" */");
        
        assertThat(javaDoc.javaDocLines().count(), is(3l));
    }
}
