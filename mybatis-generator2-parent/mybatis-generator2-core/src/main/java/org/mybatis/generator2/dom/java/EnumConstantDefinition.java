package org.mybatis.generator2.dom.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class EnumConstantDefinition extends JavaDomNode<EnumConstantDefinition> {

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
    
    @Override
    public EnumConstantDefinition deepCopy() {
        return EnumConstantDefinition.of(name,
                javaDoc == null ? null : javaDoc.deepCopy(),
                arguments().map(Argument::deepCopy));
    }
    
    public String getName() {
        return name;
    }
    
    public Optional<JavaDoc> getJavaDoc() {
        return Optional.ofNullable(javaDoc);
    }
    
    public boolean hasArguments() {
        return !arguments.isEmpty();
    }
    
    public Stream<Argument> arguments() {
        return arguments.stream();
    }

    public static EnumConstantDefinition of(String name, Argument...arguments) {
        return of(name, null, arguments);
    }

    public static EnumConstantDefinition of(String name, JavaDoc javaDoc, Argument...arguments) {
        return of(name, javaDoc, Arrays.stream(arguments));
    }

    public static EnumConstantDefinition of(String name, JavaDoc javaDoc, Stream<Argument> arguments) {
        EnumConstantDefinition enumConstantDefinition = new EnumConstantDefinition();
        enumConstantDefinition.name = name;
        if (javaDoc != null) {
            javaDoc.parent = enumConstantDefinition;
            enumConstantDefinition.javaDoc = javaDoc;
        }
        
        arguments.forEach(a -> {
            a.parent = enumConstantDefinition;
            enumConstantDefinition.arguments.add(a);
        });
        
        return enumConstantDefinition;
    }
}
