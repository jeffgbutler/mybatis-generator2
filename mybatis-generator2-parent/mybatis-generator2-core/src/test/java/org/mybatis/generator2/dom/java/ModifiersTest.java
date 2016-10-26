package org.mybatis.generator2.dom.java;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class ModifiersTest {

    @Test
    public void testOrdering() {
        Modifiers modifiers = Modifiers.of(JavaModifier.STRICTFP);
        modifiers = modifiers.with(JavaModifier.STATIC);
        modifiers = modifiers.with(JavaModifier.PUBLIC);
        
        StringBuilder buffer = new StringBuilder();
        modifiers.modifiers().forEach(m -> {
            buffer.append(m.getKeyword());
            buffer.append(' ');
        });
        
        assertThat(buffer.toString(), is("public static strictfp "));
    }
}
