package org.mybatis.generator2.dom.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class EnumConstantDefinition extends JavaDomNode {

    private JavaDoc javaDoc;
    private String name;
    private List<Argument> arguments = new ArrayList<>();
    
    private EnumConstantDefinition() {
        super();
    }

    @Override
    public void accept(JavaDomVisitor visitor) {
        visitor.visit(this);
    }
    
    @Override
    public JavaNodeType getNodeType() {
        return JavaNodeType.ENUM_CONSTANT;
    }
    
    @Override
    public boolean allowsModifier(JavaModifier javaModifier) {
        return false;
    }
    
    public String getName() {
        return name;
    }
    
    public Optional<JavaDoc> getJavaDoc() {
        return Optional.ofNullable(javaDoc);
    }
    
    public Stream<Argument> arguments() {
        return arguments.stream();
    }

    public static EnumConstantDefinition of(String name, Argument...arguments) {
        return of(name, null, arguments);
    }

    public static EnumConstantDefinition of(String name, JavaDoc javaDoc, Argument...arguments) {
        EnumConstantDefinition enumConstantDefinition = new EnumConstantDefinition();
        enumConstantDefinition.name = name;
        if (javaDoc != null) {
            javaDoc.parent = enumConstantDefinition;
            enumConstantDefinition.javaDoc = javaDoc;
        }
        
        Arrays.stream(arguments).forEach(a -> {
            a.parent = enumConstantDefinition;
            enumConstantDefinition.arguments.add(a);
        });
        
        return enumConstantDefinition;
    }
}
