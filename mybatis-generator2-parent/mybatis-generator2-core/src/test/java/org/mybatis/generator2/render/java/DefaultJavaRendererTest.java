package org.mybatis.generator2.render.java;

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
                .withParameter(Parameter.of("amount", "BigDecimal"))
                .withModifiers(Modifiers.of(JavaModifier.PUBLIC))
                .withBodyLine("this.amount = amount;")
                .build();
        methods.add(md);
        
        
        FieldDefinition fd = new FieldDefinition.Builder("amount", "BigDecimal")
                .withModifiers(Modifiers.of(JavaModifier.PRIVATE))
                .build();
        
        ClassDefinition cd = new ClassDefinition.Builder("TestClass")
                .withField(fd)
                .withMethods(methods.stream())
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
