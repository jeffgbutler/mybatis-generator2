package org.mybatis.generator2.dom.java;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.mybatis.generator2.dom.java.JavaDomNode.JavaNodeType;

public class EnumDefinitionTest {

    @Test
    public void testGetters() {
        EnumDefinition ed = new EnumDefinition.Builder("TestEnum")
                .withJavaDoc(new JavaDoc.Builder().withJavaDocLine("/** */").build())
                .build();
        
        assertThat(ed.getJavaDoc().isPresent(), is(true));
        assertThat(ed.getModifierSet().javaModifiers().count(), is(0L));
        assertThat(ed.getName(), is("TestEnum"));
        assertThat(ed.getNodeType(), is(JavaNodeType.ENUM));
        assertThat(ed.getParent(), is(nullValue()));
    }

    @Test
    public void testModifierRules() {
        EnumDefinition ed = new EnumDefinition.Builder("TestEnum")
                .build();
        
        assertThat(ed.allowsModifier(JavaModifier.PUBLIC), is(true));
        assertThat(ed.allowsModifier(JavaModifier.PROTECTED), is(true));
        assertThat(ed.allowsModifier(JavaModifier.PRIVATE), is(true));
        assertThat(ed.allowsModifier(JavaModifier.ABSTRACT), is(false));
        assertThat(ed.allowsModifier(JavaModifier.DEFAULT), is(false));
        assertThat(ed.allowsModifier(JavaModifier.STATIC), is(false));
        assertThat(ed.allowsModifier(JavaModifier.FINAL), is(false));
        assertThat(ed.allowsModifier(JavaModifier.TRANSIENT), is(false));
        assertThat(ed.allowsModifier(JavaModifier.VOLATILE), is(false));
        assertThat(ed.allowsModifier(JavaModifier.SYNCHRONIZED), is(false));
        assertThat(ed.allowsModifier(JavaModifier.NATIVE), is(false));
        assertThat(ed.allowsModifier(JavaModifier.STRICTFP), is(true));
    }
}
