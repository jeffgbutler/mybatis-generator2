package org.mybatis.generator2.dom.java;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class EnumConstantDefinitionTest {

    @Test
    public void testConstantWithNoJavaDoc() {
        EnumConstantDefinition enumConstantDefinition = EnumConstantDefinition.of("FRED");
        assertThat(enumConstantDefinition.getName(), is("FRED"));
        assertThat(enumConstantDefinition.getJavaDoc().isPresent(), is(false));
    }

    @Test
    public void testConstantWithPregeneratedJavadoc() {
        List<String> javadocLines = new ArrayList<>();
        javadocLines.add("/**");
        javadocLines.add(" * Test line");
        javadocLines.add(" */");
        
        JavaDoc javaDoc = JavaDoc.of(javadocLines.stream());
        
        EnumConstantDefinition enumConstantDefinition = EnumConstantDefinition.of("FRED", javaDoc);
        
        assertThat(enumConstantDefinition.getName(), is("FRED"));
        assertThat(enumConstantDefinition.getJavaDoc().isPresent(), is(true));
        assertThat(enumConstantDefinition.getJavaDoc().get().javaDocLines().count(), is(3l));
    }
}
