package org.mybatis.generator2.render.java;

import static org.junit.Assert.assertThat;
import static org.mybatis.generator2.test_support.ResourceFileMatcher.matchesResourceFile;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mybatis.generator2.dom.java.ClassDefinition;
import org.mybatis.generator2.dom.java.CompilationUnit;
import org.mybatis.generator2.dom.java.ConstructorDefinition;
import org.mybatis.generator2.dom.java.FieldDefinition;
import org.mybatis.generator2.dom.java.ImportDefinition;
import org.mybatis.generator2.dom.java.JavaModifier;
import org.mybatis.generator2.dom.java.MethodDefinition;
import org.mybatis.generator2.dom.java.Parameter;

public class ClassRendererTest {

    @Test
    public void testBasicClass() {
        List<MethodDefinition> methods = new ArrayList<>();
        MethodDefinition md = new MethodDefinition.Builder("BigDecimal", "getAmount")
                .withModifier(JavaModifier.PUBLIC)
                .withBodyLine("return amount;")
                .build();
        methods.add(md);
        
        md = new MethodDefinition.Builder("void", "setAmount")
                .withParameter(Parameter.of("BigDecimal", "amount"))
                .withModifier(JavaModifier.PUBLIC)
                .withBodyLine("this.amount = amount;")
                .build();
        methods.add(md);
        
        MethodDefinition addMethod = new MethodDefinition.Builder("int", "add")
                .withModifier(JavaModifier.PUBLIC)
                .withModifier(JavaModifier.STATIC)
                .withParameter(Parameter.of("int", "a"))
                .withParameter(Parameter.of("int", "b"))
                .withBodyLine("return a + b;")
                .build();
        
        List<String> bodyLines = new ArrayList<>();
        bodyLines.add("boolean bool = false;");
        bodyLines.add("");
        bodyLines.add("if (bool) {");
        bodyLines.add("System.out.println(\"It's true!\");");
        bodyLines.add("} else {");
        bodyLines.add("System.out.println(\"It's false!\");");
        bodyLines.add("}");
        bodyLines.add("");
        bodyLines.add("int number = 0;");
        bodyLines.add("");
        bodyLines.add("switch (number) {");
        bodyLines.add("case 0:");
        bodyLines.add("case 1:");
        bodyLines.add("System.out.println(\"It's zero or one!\");");
        bodyLines.add("break;");
        bodyLines.add("case 2:");
        bodyLines.add("System.out.println(\"It's two!\");");
        bodyLines.add("break;");
        bodyLines.add("");
        bodyLines.add("default:");
        bodyLines.add("System.out.println(\"It's not zero or one or two!\");");
        bodyLines.add("}");
        bodyLines.add("");
        bodyLines.add("System.out.println(\"End of method\");");
        
        MethodDefinition weirdMethod = new MethodDefinition.Builder("void", "weirdMethod")
                .withModifier(JavaModifier.PUBLIC)
                .withBodyLines(bodyLines.stream())
                .build();
        
        FieldDefinition fd = new FieldDefinition.Builder("BigDecimal", "amount")
                .withModifier(JavaModifier.PRIVATE)
                .build();
        
        ClassDefinition cd = new ClassDefinition.Builder("BasicClass")
                .withModifier(JavaModifier.PUBLIC)
                .withSuperInterface("Serializable")
                .withField(fd)
                .withMethods(methods.stream())
                .withMethod(addMethod)
                .withMethod(weirdMethod)
                .build();
        
        CompilationUnit compilationUnit = new CompilationUnit.Builder()
                .inPackage("org.mybatis.generator.test")
                .withImport(ImportDefinition.of("java.math.BigDecimal"))
                .withImport("java.io.Serializable")
                .withClassDefinition(cd)
                .build();
        
        DefaultJavaRenderer renderer = DefaultJavaRenderer.of(compilationUnit);
        String output = renderer.render();
        assertThat(output, matchesResourceFile("/org/mybatis/generator2/dom/java/BasicClass.java"));
    }

    @Test
    public void testClassWithInnerClass() {
        MethodDefinition getAmountMethod = new MethodDefinition.Builder("BigDecimal", "getAmount")
                .withModifier(JavaModifier.PUBLIC)
                .withBodyLine("return amount;")
                .build();
        
        ConstructorDefinition constructor = new ConstructorDefinition.Builder()
                .withModifier(JavaModifier.PRIVATE)
                .build();
        
        FieldDefinition fd = new FieldDefinition.Builder("BigDecimal", "amount")
                .withModifier(JavaModifier.PRIVATE)
                .build();
        
        ClassDefinition cd = new ClassDefinition.Builder("ClassWithInnerClass")
                .withModifier(JavaModifier.PUBLIC)
                .withField(fd)
                .withConstructor(constructor)
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
                .withModifier(JavaModifier.PRIVATE)
                .build();
        
        MethodDefinition withAmountMethod = new MethodDefinition.Builder("Builder", "withAmount")
                .withModifier(JavaModifier.PUBLIC)
                .withParameter(Parameter.of("amount", "BigDecimal"))
                .withBodyLine("testClass.amount = amount;")
                .withBodyLine("return this;")
                .build();
        
        MethodDefinition buildMethod = new MethodDefinition.Builder("TestClass", "build")
                .withModifier(JavaModifier.PUBLIC)
                .withBodyLine("return testClass;")
                .build();

        ClassDefinition classDefinition = new ClassDefinition.Builder("Builder")
                .withModifier(JavaModifier.PUBLIC)
                .withModifier(JavaModifier.STATIC)
                .withField(fd)
                .withMethod(withAmountMethod)
                .withMethod(buildMethod)
                .build();
        
        return classDefinition;
    }
}
