package org.mybatis.generator2.dom.java;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.mybatis.generator2.dom.java.JavaDomNode.JavaNodeType;

public class ArgumentTest {

    @Test
    public void testGettersWithInt() {
        Argument argument = Argument.of(1);

        assertThat(argument.getParent(), is(nullValue()));
        assertThat(argument.getNodeType(), is(JavaNodeType.ARGUMENT));
        assertThat(argument.getValue(), is("1"));
        assertThat(argument.isString(), is(false));
    }

    @Test
    public void testGettersWithString() {
        Argument argument = Argument.of("1");

        assertThat(argument.getParent(), is(nullValue()));
        assertThat(argument.getNodeType(), is(JavaNodeType.ARGUMENT));
        assertThat(argument.getValue(), is("1"));
        assertThat(argument.isString(), is(true));
    }
    
    @Test
    public void testGettersWithNonString() {
        Argument argument = Argument.of("new Date()", false);

        assertThat(argument.getParent(), is(nullValue()));
        assertThat(argument.getNodeType(), is(JavaNodeType.ARGUMENT));
        assertThat(argument.getValue(), is("new Date()"));
        assertThat(argument.isString(), is(false));
    }
    
    @Test
    public void testModifierRules() {
        Argument argument = Argument.of(1);
        
        assertThat(argument.allowsModifier(JavaModifier.PUBLIC), is(false));
        assertThat(argument.allowsModifier(JavaModifier.PROTECTED), is(false));
        assertThat(argument.allowsModifier(JavaModifier.PRIVATE), is(false));
        assertThat(argument.allowsModifier(JavaModifier.ABSTRACT), is(false));
        assertThat(argument.allowsModifier(JavaModifier.DEFAULT), is(false));
        assertThat(argument.allowsModifier(JavaModifier.STATIC), is(false));
        assertThat(argument.allowsModifier(JavaModifier.FINAL), is(false));
        assertThat(argument.allowsModifier(JavaModifier.TRANSIENT), is(false));
        assertThat(argument.allowsModifier(JavaModifier.VOLATILE), is(false));
        assertThat(argument.allowsModifier(JavaModifier.SYNCHRONIZED), is(false));
        assertThat(argument.allowsModifier(JavaModifier.NATIVE), is(false));
        assertThat(argument.allowsModifier(JavaModifier.STRICTFP), is(false));
    }
}
