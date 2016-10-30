package org.mybatis.generator2.dom.java;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class ModifiersTest {

    @Test
    public void testOrdering() {
        Set<JavaModifier> modifiers = new HashSet<>();
        modifiers.add(JavaModifier.STRICTFP);
        modifiers.add(JavaModifier.STATIC);
        modifiers.add(JavaModifier.PUBLIC);
        
        ModifierSet modifierSet = new ModifierSet(null, modifiers);
        
        StringBuilder buffer = new StringBuilder();
        modifierSet.javaModifiers().forEach(m -> {
            buffer.append(m.getKeyword());
            buffer.append(' ');
        });
        
        assertThat(buffer.toString(), is("public static strictfp "));
    }
}
