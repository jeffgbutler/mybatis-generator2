package org.mybatis.generator2.dom.java;

import static org.hamcrest.core.Is.*;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.mybatis.generator2.dom.java.JavaDomNode.JavaNodeType;

public class ParameterTest {

    @Test
    public void testGetters() {
        Parameter parameter = new Parameter.Builder("BigDecimal", "amount")
                .withModifier(JavaModifier.FINAL)
                .isVarArgs(true)
                .build();
        assertThat(parameter.getName(), is("amount"));
        assertThat(parameter.getType(), is("BigDecimal"));
        assertThat(parameter.getModifierSet().isFinal(), is(true));
        assertThat(parameter.isVarArgs(), is(true));
        assertThat(parameter.getNodeType(), is(JavaNodeType.PARAMETER));
    }
    
    @Test
    public void testModifierRules() {
        Parameter parameter = new Parameter.Builder("BigDecimal", "amount")
                .build();

        assertThat(parameter.allowsModifier(JavaModifier.PUBLIC), is(false));
        assertThat(parameter.allowsModifier(JavaModifier.PROTECTED), is(false));
        assertThat(parameter.allowsModifier(JavaModifier.PRIVATE), is(false));
        assertThat(parameter.allowsModifier(JavaModifier.ABSTRACT), is(false));
        assertThat(parameter.allowsModifier(JavaModifier.DEFAULT), is(false));
        assertThat(parameter.allowsModifier(JavaModifier.STATIC), is(false));
        assertThat(parameter.allowsModifier(JavaModifier.FINAL), is(true));
        assertThat(parameter.allowsModifier(JavaModifier.TRANSIENT), is(false));
        assertThat(parameter.allowsModifier(JavaModifier.VOLATILE), is(false));
        assertThat(parameter.allowsModifier(JavaModifier.SYNCHRONIZED), is(false));
        assertThat(parameter.allowsModifier(JavaModifier.NATIVE), is(false));
        assertThat(parameter.allowsModifier(JavaModifier.STRICTFP), is(false));
    }
}
