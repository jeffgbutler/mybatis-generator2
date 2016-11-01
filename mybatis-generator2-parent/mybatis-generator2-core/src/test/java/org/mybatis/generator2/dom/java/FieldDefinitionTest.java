package org.mybatis.generator2.dom.java;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.mybatis.generator2.dom.java.JavaDomNode.JavaNodeType;

public class FieldDefinitionTest {

    @Test
    public void testGetters() {
        FieldDefinition fd = new FieldDefinition.Builder("BigDecimal", "amount")
                .withInitializationString("BigDecimal.ONE")
                .withJavaDoc(new JavaDoc.Builder().withJavaDocLine("/** */").build())
                .build();
        
        assertThat(fd.getJavaDoc().isPresent(), is(true));
        assertThat(fd.getModifierSet().javaModifiers().count(), is(0L));
        assertThat(fd.getName(), is("amount"));
        assertThat(fd.getType(), is("BigDecimal"));
        assertThat(fd.getInitializationString().get(), is("BigDecimal.ONE"));
        assertThat(fd.getNodeType(), is(JavaNodeType.FIELD));
        assertThat(fd.getParent(), is(nullValue()));
    }

    @Test
    public void testModifierRulesWithClassField() {
        FieldDefinition fd = new FieldDefinition.Builder("BigDecimal", "amount")
                .build();
        ClassDefinition cd = new ClassDefinition.Builder("TestClass")
                .withField(fd)
                .build();
        
        assertThat(fd.getJavaDoc().isPresent(), is(false));
        assertThat(fd.getModifierSet().javaModifiers().count(), is(0L));
        assertThat(fd.getName(), is("amount"));
        assertThat(fd.getType(), is("BigDecimal"));
        assertThat(fd.getInitializationString().isPresent(), is(false));
        assertThat(fd.getNodeType(), is(JavaNodeType.FIELD));
        assertThat(fd.getParent() == cd, is(true));
        
        assertThat(fd.allowsModifier(JavaModifier.PUBLIC), is(true));
        assertThat(fd.allowsModifier(JavaModifier.PROTECTED), is(true));
        assertThat(fd.allowsModifier(JavaModifier.PRIVATE), is(true));
        assertThat(fd.allowsModifier(JavaModifier.ABSTRACT), is(false));
        assertThat(fd.allowsModifier(JavaModifier.DEFAULT), is(false));
        assertThat(fd.allowsModifier(JavaModifier.STATIC), is(true));
        assertThat(fd.allowsModifier(JavaModifier.FINAL), is(true));
        assertThat(fd.allowsModifier(JavaModifier.TRANSIENT), is(true));
        assertThat(fd.allowsModifier(JavaModifier.VOLATILE), is(true));
        assertThat(fd.allowsModifier(JavaModifier.SYNCHRONIZED), is(false));
        assertThat(fd.allowsModifier(JavaModifier.NATIVE), is(false));
        assertThat(fd.allowsModifier(JavaModifier.STRICTFP), is(false));
    }

    @Test
    public void testModifierRulesWithEnumField() {
        FieldDefinition fd = new FieldDefinition.Builder("BigDecimal", "amount")
                .build();
        EnumDefinition ed = new EnumDefinition.Builder("TestEnum")
                .withField(fd)
                .build();
        
        assertThat(fd.getJavaDoc().isPresent(), is(false));
        assertThat(fd.getModifierSet().javaModifiers().count(), is(0L));
        assertThat(fd.getName(), is("amount"));
        assertThat(fd.getType(), is("BigDecimal"));
        assertThat(fd.getInitializationString().isPresent(), is(false));
        assertThat(fd.getNodeType(), is(JavaNodeType.FIELD));
        assertThat(fd.getParent() == ed, is(true));
        
        assertThat(fd.allowsModifier(JavaModifier.PUBLIC), is(true));
        assertThat(fd.allowsModifier(JavaModifier.PROTECTED), is(true));
        assertThat(fd.allowsModifier(JavaModifier.PRIVATE), is(true));
        assertThat(fd.allowsModifier(JavaModifier.ABSTRACT), is(false));
        assertThat(fd.allowsModifier(JavaModifier.DEFAULT), is(false));
        assertThat(fd.allowsModifier(JavaModifier.STATIC), is(true));
        assertThat(fd.allowsModifier(JavaModifier.FINAL), is(true));
        assertThat(fd.allowsModifier(JavaModifier.TRANSIENT), is(true));
        assertThat(fd.allowsModifier(JavaModifier.VOLATILE), is(true));
        assertThat(fd.allowsModifier(JavaModifier.SYNCHRONIZED), is(false));
        assertThat(fd.allowsModifier(JavaModifier.NATIVE), is(false));
        assertThat(fd.allowsModifier(JavaModifier.STRICTFP), is(false));
    }

    @Test
    public void testModifierRulesWithInterfaceField() {
        FieldDefinition fd = new FieldDefinition.Builder("BigDecimal", "amount")
                .build();
        InterfaceDefinition id = new InterfaceDefinition.Builder("TestInterface")
                .withField(fd)
                .build();
        
        assertThat(fd.getJavaDoc().isPresent(), is(false));
        assertThat(fd.getModifierSet().javaModifiers().count(), is(0L));
        assertThat(fd.getName(), is("amount"));
        assertThat(fd.getType(), is("BigDecimal"));
        assertThat(fd.getInitializationString().isPresent(), is(false));
        assertThat(fd.getNodeType(), is(JavaNodeType.FIELD));
        assertThat(fd.getParent() == id, is(true));
        
        assertThat(fd.allowsModifier(JavaModifier.PUBLIC), is(false));
        assertThat(fd.allowsModifier(JavaModifier.PROTECTED), is(false));
        assertThat(fd.allowsModifier(JavaModifier.PRIVATE), is(false));
        assertThat(fd.allowsModifier(JavaModifier.ABSTRACT), is(false));
        assertThat(fd.allowsModifier(JavaModifier.DEFAULT), is(false));
        assertThat(fd.allowsModifier(JavaModifier.STATIC), is(false));
        assertThat(fd.allowsModifier(JavaModifier.FINAL), is(false));
        assertThat(fd.allowsModifier(JavaModifier.TRANSIENT), is(false));
        assertThat(fd.allowsModifier(JavaModifier.VOLATILE), is(false));
        assertThat(fd.allowsModifier(JavaModifier.SYNCHRONIZED), is(false));
        assertThat(fd.allowsModifier(JavaModifier.NATIVE), is(false));
        assertThat(fd.allowsModifier(JavaModifier.STRICTFP), is(false));
    }

    @Test
    public void testDeepCopy() {
        FieldDefinition fd = new FieldDefinition.Builder("BigDecimal", "amount")
                .withJavaDoc(new JavaDoc.Builder().withJavaDocLine("/** */").build())
                .build();
        InterfaceDefinition id = new InterfaceDefinition.Builder("TestInterface")
                .withField(fd)
                .build();
        
        assertThat(fd.getJavaDoc().get().getParent() == fd, is(true));
        assertThat(fd.getModifierSet().javaModifiers().count(), is(0L));
        assertThat(fd.getName(), is("amount"));
        assertThat(fd.getType(), is("BigDecimal"));
        assertThat(fd.getInitializationString().isPresent(), is(false));
        assertThat(fd.getNodeType(), is(JavaNodeType.FIELD));
        assertThat(fd.getParent() == id, is(true));
        
        InterfaceDefinition id2 = id.deepCopy();
        id2.fields().findFirst().ifPresent(fd2 -> {
            assertThat(fd2.getJavaDoc().get().getParent() == fd2, is(true));
            assertThat(fd2.getModifierSet().javaModifiers().count(), is(0L));
            assertThat(fd2.getName(), is("amount"));
            assertThat(fd2.getType(), is("BigDecimal"));
            assertThat(fd2.getInitializationString().isPresent(), is(false));
            assertThat(fd2.getNodeType(), is(JavaNodeType.FIELD));
            assertThat(fd2.getParent() == id2, is(true));
        });
    }
}
