package org.mybatis.generator2.dom.java;

public class Argument extends JavaDomNode {
    private String value;
    private boolean isString;

    private Argument() {
        super();
    }

    @Override
    public void accept(JavaDomVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public JavaNodeType getNodeType() {
        return JavaNodeType.ARGUMENT;
    }

    @Override
    public boolean allowsModifier(JavaModifier javaModifier) {
        return false;
    }

    public String getValue() {
        return value;
    }

    public boolean isString() {
        return isString;
    }

    public static Argument of(String value) {
        return of(value, false);
    }

    public static Argument of(String value, boolean isString) {
        Argument argument = new Argument();
        argument.value = value;
        argument.isString = isString;
        return argument;
    }
}
