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
}
