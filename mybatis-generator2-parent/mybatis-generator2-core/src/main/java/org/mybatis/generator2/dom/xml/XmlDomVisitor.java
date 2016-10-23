package org.mybatis.generator2.dom.xml;

public interface XmlDomVisitor {

    default boolean visit(Attribute attribute) {
        return true;
    }

    default boolean visit(Document document) {
        return true;
    }

    default boolean visit(TextElement textElement) {
        return true;
    }

    default boolean visit(XmlElement xmlElement) {
        return true;
    }
}
