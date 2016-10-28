package org.mybatis.generator2.dom.java;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class ModifiersTest {

    @Test
    public void testOrdering() {
        ModifierSet modifierSet = new ModifierSet(null);
        modifierSet.javaModifiers.add(JavaModifier.STRICTFP);
        modifierSet.javaModifiers.add(JavaModifier.STATIC);
        modifierSet.javaModifiers.add(JavaModifier.PUBLIC);
        
        StringBuilder buffer = new StringBuilder();
        modifierSet.javaModifiers().forEach(m -> {
            buffer.append(m.getKeyword());
            buffer.append(' ');
        });
        
        assertThat(buffer.toString(), is("public static strictfp "));
    }
}
