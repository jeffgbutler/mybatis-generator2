package org.mybatis.generator2.dom.java;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mybatis.generator2.dom.java.JavaDomNode.JavaNodeType;

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

    @Test
    public void testModifierRules() {
        EnumConstantDefinition enumConstantDefinition = EnumConstantDefinition.of("FRED");
        assertThat(enumConstantDefinition.getNodeType(), is(JavaNodeType.ENUM_CONSTANT));
        
        assertThat(enumConstantDefinition.allowsModifier(JavaModifier.PUBLIC), is(false));
        assertThat(enumConstantDefinition.allowsModifier(JavaModifier.PROTECTED), is(false));
        assertThat(enumConstantDefinition.allowsModifier(JavaModifier.PRIVATE), is(false));
        assertThat(enumConstantDefinition.allowsModifier(JavaModifier.ABSTRACT), is(false));
        assertThat(enumConstantDefinition.allowsModifier(JavaModifier.DEFAULT), is(false));
        assertThat(enumConstantDefinition.allowsModifier(JavaModifier.STATIC), is(false));
        assertThat(enumConstantDefinition.allowsModifier(JavaModifier.FINAL), is(false));
        assertThat(enumConstantDefinition.allowsModifier(JavaModifier.TRANSIENT), is(false));
        assertThat(enumConstantDefinition.allowsModifier(JavaModifier.VOLATILE), is(false));
        assertThat(enumConstantDefinition.allowsModifier(JavaModifier.SYNCHRONIZED), is(false));
        assertThat(enumConstantDefinition.allowsModifier(JavaModifier.NATIVE), is(false));
        assertThat(enumConstantDefinition.allowsModifier(JavaModifier.STRICTFP), is(false));
    }
}
