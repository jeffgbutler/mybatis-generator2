package org.mybatis.generator2.render.java;

import static org.junit.Assert.assertThat;
import static org.mybatis.generator2.test_support.ResourceFileMatcher.matchesResourceFile;

import org.junit.Test;
import org.mybatis.generator2.dom.java.CompilationUnit;
import org.mybatis.generator2.dom.java.FieldDefinition;
import org.mybatis.generator2.dom.java.InterfaceDefinition;
import org.mybatis.generator2.dom.java.JavaModifier;
import org.mybatis.generator2.dom.java.MethodDefinition;
import org.mybatis.generator2.dom.java.Parameter;

public class InterfaceRendererTest {

    @Test
    public void testBasicInterface() {
        
        FieldDefinition fd = new FieldDefinition.Builder("int", "value")
                // should be ignored
                .withModifier(JavaModifier.PRIVATE)
                .withInitializationString("0")
                .build();
        
        MethodDefinition md1 = new MethodDefinition.Builder("int", "getValue")
                // should be ignored
                .withModifier(JavaModifier.PUBLIC)
                .withModifier(JavaModifier.STATIC)
                .withBodyLine("return value;")
                .build();

        MethodDefinition md2 = new MethodDefinition.Builder("int", "add")
                // should be ignored
                .withModifier(JavaModifier.PUBLIC)
                .withModifier(JavaModifier.DEFAULT)
                .withParameter(Parameter.of("int", "a"))
                .withParameter(Parameter.of("int", "b"))
                .withBodyLine("return a + b;")
                .build();

        MethodDefinition md3 = new MethodDefinition.Builder("int", "doSomething")
                // should be ignored
                .withModifier(JavaModifier.PUBLIC)
                .build();
        
        InterfaceDefinition id = new InterfaceDefinition.Builder("BasicInterface")
                .withModifier(JavaModifier.PUBLIC)
                .withSuperInterface("Serializable")
                .withField(fd)
                .withMethod(md1)
                .withMethod(md2)
                .withMethod(md3)
                .build();
        
        CompilationUnit cu = new CompilationUnit.Builder()
                .inPackage("org.mybatis.generator.test")
                .withImport("java.io.Serializable")
                .withInterfaceDefinition(id)
                .build();

        DefaultJavaRenderer renderer = DefaultJavaRenderer.of(cu);
        String output = renderer.render();
        assertThat(output, matchesResourceFile("/org/mybatis/generator2/dom/java/BasicInterface.java"));
    }
}
