package org.mybatis.generator2.render.java;

import org.junit.Test;
import org.mybatis.generator2.dom.java.ClassDefinition;
import org.mybatis.generator2.dom.java.CompilationUnit;
import org.mybatis.generator2.dom.java.FieldDefinition;
import org.mybatis.generator2.dom.java.ImportDefinition;
import org.mybatis.generator2.dom.java.JavaModifier;
import org.mybatis.generator2.dom.java.Modifiers;

public class DefaultJavaRendererTest {

    @Test
    public void testBasicClass() {
        FieldDefinition fd = new FieldDefinition.Builder("amount", "BigDecimal")
                .withModifiers(Modifiers.of(JavaModifier.PRIVATE))
                .build();
        
        ClassDefinition cd = new ClassDefinition.Builder("TestClass")
                .withField(fd)
                .withModifiers(Modifiers.of(JavaModifier.PUBLIC))
                .build();
        
        CompilationUnit compilationUnit = new CompilationUnit.Builder()
                .withPackage("org.mybatis.generator.test")
                .withImport(ImportDefinition.of("java.math.BigDecimal"))
                .withClassDefinition(cd)
                .build();
        
        DefaultJavaRenderer renderer = DefaultJavaRenderer.of(compilationUnit);
        String output = renderer.render();
        System.out.print(output);
    }
}
