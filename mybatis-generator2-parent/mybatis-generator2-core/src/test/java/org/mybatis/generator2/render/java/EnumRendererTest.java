package org.mybatis.generator2.render.java;

import static org.junit.Assert.assertThat;
import static org.mybatis.generator2.test_support.ResourceFileMatcher.matchesResourceFile;

import org.junit.Test;
import org.mybatis.generator2.dom.java.Argument;
import org.mybatis.generator2.dom.java.CompilationUnit;
import org.mybatis.generator2.dom.java.ConstructorDefinition;
import org.mybatis.generator2.dom.java.EnumConstantDefinition;
import org.mybatis.generator2.dom.java.EnumDefinition;
import org.mybatis.generator2.dom.java.FieldDefinition;
import org.mybatis.generator2.dom.java.JavaModifier;
import org.mybatis.generator2.dom.java.MethodDefinition;
import org.mybatis.generator2.dom.java.Parameter;

public class EnumRendererTest {

    @Test
    public void testBasicEnum() {
        ConstructorDefinition constructor = new ConstructorDefinition.Builder()
                // the PUBLIC modifier should be ignored for an enum
                .withModifier(JavaModifier.PUBLIC)
                .withModifier(JavaModifier.PRIVATE)
                .withParameter(Parameter.of("int", "value"))
                .withBodyLine("this.value = value;")
                .build();
        
        FieldDefinition fd = new FieldDefinition.Builder("int", "value")
                .withModifier(JavaModifier.PRIVATE)
                .build();
        
        MethodDefinition md = new MethodDefinition.Builder("int", "getValue")
                .withModifier(JavaModifier.PUBLIC)
                .withBodyLine("return value;")
                .build();

        EnumDefinition ed = new EnumDefinition.Builder("BasicEnum")
                .withModifier(JavaModifier.PUBLIC)
                .withEnumConstant(EnumConstantDefinition.of("FRED", Argument.of(1)))
                .withEnumConstant(EnumConstantDefinition.of("WILMA", Argument.of(2)))
                .withEnumConstant(EnumConstantDefinition.of("BARNEY", Argument.of(3)))
                .withEnumConstant(EnumConstantDefinition.of("BETTY", Argument.of(4)))
                .withConstructor(constructor)
                .withMethod(md)
                .withField(fd)
                .build();
        
        CompilationUnit cu = new CompilationUnit.Builder()
                .inPackage("org.mybatis.generator.test")
                .withEnumDefinition(ed)
                .build();

        DefaultJavaRenderer renderer = DefaultJavaRenderer.of(cu);
        String output = renderer.render();
        assertThat(output, matchesResourceFile("/org/mybatis/generator2/dom/java/BasicEnum.java"));
    }
}
