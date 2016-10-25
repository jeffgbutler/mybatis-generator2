package org.mybatis.generator2.dom.java;

import static org.hamcrest.core.Is.*;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.mybatis.generator2.dom.java.InterfaceDefinition;

public class InterfaceDefinitionTest {

    @Test
    public void testGetters() {
        InterfaceDefinition interfaceDefinition = new InterfaceDefinition.Builder()
                .withName("fred")
                .build();
        assertThat(interfaceDefinition.getName(), is("fred"));
    }
}
