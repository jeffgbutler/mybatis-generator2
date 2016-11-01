package org.mybatis.generator2.dom.java;

public class Argument extends JavaDomNode<Argument> {
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
    
    @Override
    public Argument deepCopy() {
        return Argument.of(value, isString);
    }

    public String getValue() {
        return value;
    }

    /**
     * If true a renderer should surround the value with quotation marks
     * 
     * @return
     */
    public boolean isString() {
        return isString;
    }

    public static Argument of(int value) {
        return of(Integer.toString(value), false);
    }

    public static Argument of(String value) {
        return of(value, true);
    }
    
    /**
     * Use this builder if the argument is something other than an int or a string.
     * For example:
     * 
     * Argument argument = Argument.of("LocalDate.now()", false);
     * 
     * @param value
     * @param isString if the value is a string, then the renderers are expected
     *   to surround the value with quotation marks
     * @return
     */
    public static Argument of(String value, boolean isString) {
        Argument argument = new Argument();
        argument.value = value;
        argument.isString = isString;
        return argument;
    }
}
