package org.mybatis.generator2.dom.xml;

public interface XmlDomVisitor {

    default void visit(Attribute attribute) {
        return;
    }

    default boolean visit(Document document) {
        return true;
    }

    default void visit(TextElement textElement) {
        return;
    }

    default boolean visit(XmlElement xmlElement) {
        return true;
    }
}
