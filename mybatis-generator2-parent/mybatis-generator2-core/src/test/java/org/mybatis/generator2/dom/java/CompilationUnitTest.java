package org.mybatis.generator2.dom.java;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.mybatis.generator2.dom.java.JavaDomNode.JavaNodeType;

public class CompilationUnitTest {

    @Test
    public void testGettersWithInt() {
        CompilationUnit cu = new CompilationUnit.Builder()
                .inPackage("com.test")
                .withImport("java.util.Map")
                .withJavaDoc(new JavaDoc.Builder().withJavaDocLine("/** */").build())
                .build();

        assertThat(cu.getParent(), is(nullValue()));
        assertThat(cu.getNodeType(), is(JavaNodeType.COMPILATION_UNIT));
        assertThat(cu.getPackage().get(), is("com.test"));
        assertThat(cu.nonStaticImports().count(), is(1L));
        assertThat(cu.getJavaDoc().get().getParent() == cu, is(true));
        assertThat(cu.staticImports().count(), is(0L));
        
        CompilationUnit cu2 = cu.deepCopy();
        assertThat(cu2.getParent(), is(nullValue()));
        assertThat(cu2.getNodeType(), is(JavaNodeType.COMPILATION_UNIT));
        assertThat(cu2.getPackage().get(), is("com.test"));
        assertThat(cu2.nonStaticImports().count(), is(1L));
        assertThat(cu2.getJavaDoc().get().getParent() == cu2, is(true));
        assertThat(cu2.staticImports().count(), is(0L));
    }

    @Test
    public void testModifierRules() {
        CompilationUnit cu = new CompilationUnit.Builder()
                .inPackage("com.test")
                .withImport("java.util.Map")
                .withJavaDoc(new JavaDoc.Builder().withJavaDocLine("/** */").build())
                .build();
        
        assertThat(cu.allowsModifier(JavaModifier.PUBLIC), is(false));
        assertThat(cu.allowsModifier(JavaModifier.PROTECTED), is(false));
        assertThat(cu.allowsModifier(JavaModifier.PRIVATE), is(false));
        assertThat(cu.allowsModifier(JavaModifier.ABSTRACT), is(false));
        assertThat(cu.allowsModifier(JavaModifier.DEFAULT), is(false));
        assertThat(cu.allowsModifier(JavaModifier.STATIC), is(false));
        assertThat(cu.allowsModifier(JavaModifier.FINAL), is(false));
        assertThat(cu.allowsModifier(JavaModifier.TRANSIENT), is(false));
        assertThat(cu.allowsModifier(JavaModifier.VOLATILE), is(false));
        assertThat(cu.allowsModifier(JavaModifier.SYNCHRONIZED), is(false));
        assertThat(cu.allowsModifier(JavaModifier.NATIVE), is(false));
        assertThat(cu.allowsModifier(JavaModifier.STRICTFP), is(false));
    }
}
