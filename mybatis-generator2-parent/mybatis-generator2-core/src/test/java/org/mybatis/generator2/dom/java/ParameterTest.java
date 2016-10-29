package org.mybatis.generator2.dom.java;

import static org.hamcrest.core.Is.*;
import static org.junit.Assert.assertThat;
import org.junit.Test;

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
    }
}
