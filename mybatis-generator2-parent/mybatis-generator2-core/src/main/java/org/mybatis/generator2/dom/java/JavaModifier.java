package org.mybatis.generator2.dom.java;

public enum JavaModifier {
    // important - these should be in order recommended by the JLS
    
    PUBLIC("public"),
    PROTECTED("protected"),
    PRIVATE("private"),
    ABSTRACT("abstract"),
    DEFAULT("default"),
    STATIC("static"),
    FINAL("final"),
    TRANSIENT("transient"),
    VOLATILE("volatile"),
    SYNCHRONIZED("synchronized"),
    NATIVE("native"),
    STRICTFP("strictfp");
    
    private String keyword;
    
    private JavaModifier(String keyword) {
        this.keyword = keyword;
    }
    
    public String getKeyword() {
        return keyword;
    }
    
    /**
     * This method checks to see if a modifier is applicable in the context.
     * For example, the public modifier is not applicable to an interface method.
     *  
     * @param modifiersParent the parent node of the {@link Modifiers} object that
     *    contains this modifier (could be a method, parameter, field, class, enum, interface, etc.)
     * @return true if the modifier can be applied to the parent node
     */
    public boolean isApplicable(JavaDomNode modifiersParent) {
        return modifiersParent.allowsModifier(this);
    }
}
