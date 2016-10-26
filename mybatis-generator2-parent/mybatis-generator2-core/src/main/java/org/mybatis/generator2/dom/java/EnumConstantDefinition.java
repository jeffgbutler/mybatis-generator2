package org.mybatis.generator2.dom.java;

import java.util.Optional;

public class EnumConstantDefinition extends JavaDomNode {

    private JavaDoc javaDoc;
    private String name;
    
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

    public static EnumConstantDefinition of(String name) {
        EnumConstantDefinition enumConstantDefinition = new EnumConstantDefinition();
        enumConstantDefinition.name = name;
        return enumConstantDefinition;
    }

    public static EnumConstantDefinition of(String name, JavaDoc javaDoc) {
        EnumConstantDefinition enumConstantDefinition = new EnumConstantDefinition();
        enumConstantDefinition.name = name;
        javaDoc.parent = enumConstantDefinition;
        enumConstantDefinition.javaDoc = javaDoc;
        return enumConstantDefinition;
    }
}
