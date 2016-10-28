package org.mybatis.generator2.render.java;

import static org.junit.Assert.assertThat;
import static org.mybatis.generator2.test_support.ResourceFileMatcher.matchesResourceFile;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mybatis.generator2.dom.java.ClassDefinition;
import org.mybatis.generator2.dom.java.CompilationUnit;
import org.mybatis.generator2.dom.java.FieldDefinition;
import org.mybatis.generator2.dom.java.ImportDefinition;
import org.mybatis.generator2.dom.java.JavaModifier;
import org.mybatis.generator2.dom.java.MethodDefinition;
import org.mybatis.generator2.dom.java.Modifiers;
import org.mybatis.generator2.dom.java.Parameter;

public class DefaultJavaRendererTest {

    @Test
    public void testBasicClass() {
        List<MethodDefinition> methods = new ArrayList<>();
        MethodDefinition md = new MethodDefinition.Builder("getAmount")
                .withReturnType("BigDecimal")
                .withModifiers(Modifiers.of(JavaModifier.PUBLIC))
                .withBodyLine("return amount;")
                .build();
        methods.add(md);
        
        md = new MethodDefinition.Builder("setAmount")
                .withReturnType("void")
                .withParameter(Parameter.of("BigDecimal", "amount"))
                .withModifiers(Modifiers.of(JavaModifier.PUBLIC))
                .withBodyLine("this.amount = amount;")
                .build();
        methods.add(md);
        
        MethodDefinition addMethod = new MethodDefinition.Builder("add")
                .withModifiers(Modifiers.of(JavaModifier.PUBLIC, JavaModifier.STATIC))
                .withReturnType("int")
                .withParameter(Parameter.of("int", "a"))
                .withParameter(Parameter.of("int", "b"))
                .withBodyLine("return a + b;")
                .build();
        
        FieldDefinition fd = new FieldDefinition.Builder("BigDecimal", "amount")
                .withModifiers(Modifiers.of(JavaModifier.PRIVATE))
                .build();
        
        ClassDefinition cd = new ClassDefinition.Builder("TestClass")
                .withField(fd)
                .withMethods(methods.stream())
                .withMethod(addMethod)
                .withModifiers(Modifiers.of(JavaModifier.PUBLIC))
                .build();
        
        CompilationUnit compilationUnit = new CompilationUnit.Builder()
                .inPackage("org.mybatis.generator.test")
                .withImport(ImportDefinition.of("java.math.BigDecimal"))
                .withClassDefinition(cd)
                .build();
        
        DefaultJavaRenderer renderer = DefaultJavaRenderer.of(compilationUnit);
        String output = renderer.render();
        assertThat(output, matchesResourceFile("/org/mybatis/generator2/dom/java/BasicClass.java"));
    }

    @Test
    public void testClassWithInnerClass() {
        MethodDefinition getAmountMethod = new MethodDefinition.Builder("getAmount")
                .withReturnType("BigDecimal")
                .withModifiers(Modifiers.of(JavaModifier.PUBLIC))
                .withBodyLine("return amount;")
                .build();
        
        MethodDefinition constructor = new MethodDefinition.Builder("TestClass")
                .withModifiers(Modifiers.of(JavaModifier.PRIVATE))
                .build();
        
        FieldDefinition fd = new FieldDefinition.Builder("BigDecimal", "amount")
                .withModifiers(Modifiers.of(JavaModifier.PRIVATE))
                .build();
        
        ClassDefinition cd = new ClassDefinition.Builder("TestClass")
                .withModifiers(Modifiers.of(JavaModifier.PUBLIC))
                .withField(fd)
                .withMethod(constructor)
                .withMethod(getAmountMethod)
                .withClassDefinition(innerClass())
                .build();
        
        CompilationUnit compilationUnit = new CompilationUnit.Builder()
                .inPackage("org.mybatis.generator.test")
                .withImport(ImportDefinition.of("java.math.BigDecimal"))
                .withClassDefinition(cd)
                .build();
        
        DefaultJavaRenderer renderer = DefaultJavaRenderer.of(compilationUnit);
        String output = renderer.render();
        assertThat(output, matchesResourceFile("/org/mybatis/generator2/dom/java/ClassWithInnerClass.java"));
    }
    
    private ClassDefinition innerClass() {
        FieldDefinition fd = new FieldDefinition.Builder("testClass", "TestClass")
                .withInitializationString("new TestClass()")
                .withModifiers(Modifiers.of(JavaModifier.PRIVATE))
                .build();
        
        MethodDefinition withAmountMethod = new MethodDefinition.Builder("withAmount")
                .withReturnType("Builder")
                .withModifiers(Modifiers.of(JavaModifier.PUBLIC))
                .withParameter(Parameter.of("amount", "BigDecimal"))
                .withBodyLine("testClass.amount = amount;")
                .withBodyLine("return this;")
                .build();
        
        MethodDefinition buildMethod = new MethodDefinition.Builder("build")
                .withReturnType("TestClass")
                .withModifiers(Modifiers.of(JavaModifier.PUBLIC))
                .withBodyLine("return testClass;")
                .build();

        ClassDefinition classDefinition = new ClassDefinition.Builder("Builder")
                .withModifiers(Modifiers.of(JavaModifier.PUBLIC, JavaModifier.STATIC))
                .withField(fd)
                .withMethod(withAmountMethod)
                .withMethod(buildMethod)
                .build();
        
        return classDefinition;
    }
}
