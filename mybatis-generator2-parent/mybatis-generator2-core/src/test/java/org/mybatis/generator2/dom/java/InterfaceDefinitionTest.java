package org.mybatis.generator2.dom.java;

import static org.hamcrest.core.IsNull.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.mybatis.generator2.dom.java.JavaDomNode.JavaNodeType;

public class InterfaceDefinitionTest {

    @Test
    public void testGetters() {
        InterfaceDefinition interfaceDefinition = new InterfaceDefinition.Builder("TestInterface")
                .build();
        assertThat(interfaceDefinition.getParent(), is(nullValue()));
        assertThat(interfaceDefinition.getName(), is("TestInterface"));
        assertThat(interfaceDefinition.getJavaDoc().isPresent(), is(false));
        assertThat(interfaceDefinition.getNodeType(), is(JavaNodeType.INTERFACE));
    }
    
    @Test
    public void testModifierRulesWithStandaloneInterface() {
        InterfaceDefinition interfaceDefinition = new InterfaceDefinition.Builder("TestInterface")
                .build();

        assertThat(interfaceDefinition.getParent(), is(nullValue()));
        assertThat(interfaceDefinition.allowsModifier(JavaModifier.PUBLIC), is(true));
        assertThat(interfaceDefinition.allowsModifier(JavaModifier.PROTECTED), is(false));
        assertThat(interfaceDefinition.allowsModifier(JavaModifier.PRIVATE), is(false));
        assertThat(interfaceDefinition.allowsModifier(JavaModifier.ABSTRACT), is(false));
        assertThat(interfaceDefinition.allowsModifier(JavaModifier.DEFAULT), is(false));
        assertThat(interfaceDefinition.allowsModifier(JavaModifier.STATIC), is(false));
        assertThat(interfaceDefinition.allowsModifier(JavaModifier.FINAL), is(false));
        assertThat(interfaceDefinition.allowsModifier(JavaModifier.TRANSIENT), is(false));
        assertThat(interfaceDefinition.allowsModifier(JavaModifier.VOLATILE), is(false));
        assertThat(interfaceDefinition.allowsModifier(JavaModifier.SYNCHRONIZED), is(false));
        assertThat(interfaceDefinition.allowsModifier(JavaModifier.NATIVE), is(false));
        assertThat(interfaceDefinition.allowsModifier(JavaModifier.STRICTFP), is(true));
    }

    @Test
    public void testModifierRulesWithInterfaceInClass() {
        InterfaceDefinition interfaceDefinition = new InterfaceDefinition.Builder("TestInterface")
                .build();
        ClassDefinition cd = new ClassDefinition.Builder("TestClass")
                .withInterfaceDefinition(interfaceDefinition)
                .build();
        
        assertThat(cd.interfaces().count(), is(1L));
        assertThat(interfaceDefinition.getParent() == cd, is(true));

        assertThat(interfaceDefinition.allowsModifier(JavaModifier.PUBLIC), is(true));
        assertThat(interfaceDefinition.allowsModifier(JavaModifier.PROTECTED), is(true));
        assertThat(interfaceDefinition.allowsModifier(JavaModifier.PRIVATE), is(true));
        assertThat(interfaceDefinition.allowsModifier(JavaModifier.ABSTRACT), is(false));
        assertThat(interfaceDefinition.allowsModifier(JavaModifier.DEFAULT), is(false));
        assertThat(interfaceDefinition.allowsModifier(JavaModifier.STATIC), is(true));
        assertThat(interfaceDefinition.allowsModifier(JavaModifier.FINAL), is(false));
        assertThat(interfaceDefinition.allowsModifier(JavaModifier.TRANSIENT), is(false));
        assertThat(interfaceDefinition.allowsModifier(JavaModifier.VOLATILE), is(false));
        assertThat(interfaceDefinition.allowsModifier(JavaModifier.SYNCHRONIZED), is(false));
        assertThat(interfaceDefinition.allowsModifier(JavaModifier.NATIVE), is(false));
        assertThat(interfaceDefinition.allowsModifier(JavaModifier.STRICTFP), is(true));
    }

    @Test
    public void testModifierRulesWithInterfaceInEnum() {
        InterfaceDefinition interfaceDefinition = new InterfaceDefinition.Builder("TestInterface")
                .build();
        EnumDefinition ed = new EnumDefinition.Builder("TestEnum")
                .withInterfaceDefinition(interfaceDefinition)
                .build();
        
        assertThat(ed.interfaces().count(), is(1L));
        assertThat(interfaceDefinition.getParent() == ed, is(true));

        assertThat(interfaceDefinition.allowsModifier(JavaModifier.PUBLIC), is(true));
        assertThat(interfaceDefinition.allowsModifier(JavaModifier.PROTECTED), is(true));
        assertThat(interfaceDefinition.allowsModifier(JavaModifier.PRIVATE), is(true));
        assertThat(interfaceDefinition.allowsModifier(JavaModifier.ABSTRACT), is(false));
        assertThat(interfaceDefinition.allowsModifier(JavaModifier.DEFAULT), is(false));
        assertThat(interfaceDefinition.allowsModifier(JavaModifier.STATIC), is(true));
        assertThat(interfaceDefinition.allowsModifier(JavaModifier.FINAL), is(false));
        assertThat(interfaceDefinition.allowsModifier(JavaModifier.TRANSIENT), is(false));
        assertThat(interfaceDefinition.allowsModifier(JavaModifier.VOLATILE), is(false));
        assertThat(interfaceDefinition.allowsModifier(JavaModifier.SYNCHRONIZED), is(false));
        assertThat(interfaceDefinition.allowsModifier(JavaModifier.NATIVE), is(false));
        assertThat(interfaceDefinition.allowsModifier(JavaModifier.STRICTFP), is(true));
    }

    @Test
    public void testModifierRulesWithInterfaceInInterface() {
        InterfaceDefinition interfaceDefinition = new InterfaceDefinition.Builder("TestInterface")
                .build();
        InterfaceDefinition id = new InterfaceDefinition.Builder("OuterInterface")
                .withInterfaceDefinition(interfaceDefinition)
                .build();
        
        assertThat(id.interfaces().count(), is(1L));
        assertThat(interfaceDefinition.getParent() == id, is(true));

        assertThat(interfaceDefinition.allowsModifier(JavaModifier.PUBLIC), is(true));
        assertThat(interfaceDefinition.allowsModifier(JavaModifier.PROTECTED), is(false));
        assertThat(interfaceDefinition.allowsModifier(JavaModifier.PRIVATE), is(false));
        assertThat(interfaceDefinition.allowsModifier(JavaModifier.ABSTRACT), is(false));
        assertThat(interfaceDefinition.allowsModifier(JavaModifier.DEFAULT), is(false));
        assertThat(interfaceDefinition.allowsModifier(JavaModifier.STATIC), is(true));
        assertThat(interfaceDefinition.allowsModifier(JavaModifier.FINAL), is(false));
        assertThat(interfaceDefinition.allowsModifier(JavaModifier.TRANSIENT), is(false));
        assertThat(interfaceDefinition.allowsModifier(JavaModifier.VOLATILE), is(false));
        assertThat(interfaceDefinition.allowsModifier(JavaModifier.SYNCHRONIZED), is(false));
        assertThat(interfaceDefinition.allowsModifier(JavaModifier.NATIVE), is(false));
        assertThat(interfaceDefinition.allowsModifier(JavaModifier.STRICTFP), is(true));
    }
}
