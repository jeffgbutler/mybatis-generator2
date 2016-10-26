package org.mybatis.generator2.dom.java;

import static org.hamcrest.core.Is.*;
import static org.junit.Assert.assertThat;
import org.junit.Test;

public class ParameterTest {

    @Test
    public void testGetters() {
        Parameter parameter = new Parameter.Builder("amount", "BigDecimal")
                .withModifiers(Modifiers.of(JavaModifier.FINAL))
                .isVarArgs(true)
                .build();
        assertThat(parameter.getName(), is("amount"));
        assertThat(parameter.getType(), is("BigDecimal"));
        assertThat(parameter.getModifiers().isPresent(), is(true));

        parameter.getModifiers().ifPresent(m -> {
            boolean isFinal = 
                     m.modifiers().filter(jm -> jm == JavaModifier.FINAL).count() == 1l;
            assertThat(isFinal, is(true));
        });
        
        assertThat(parameter.isVarArgs(), is(true));
    }
}
