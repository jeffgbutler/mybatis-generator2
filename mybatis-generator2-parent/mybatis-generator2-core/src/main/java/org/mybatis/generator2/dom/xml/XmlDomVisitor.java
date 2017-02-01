package org.mybatis.generator2.dom.xml;

public interface XmlDomVisitor<T> {
    default T visit(Attribute element) {
        return null;
    }
    
    default T visit(Document document) {
        return null;
    }

    default T visit(PublicExternalDTD publicExternalDTD) {
        return null;
    }

    default T visit(SystemExternalDTD systemExternalDTD) {
        return null;
    }
    
    default T visit(TextElement textElement) {
        return null;
    }
    
    default T visit(XmlElement xmlElement) {
        return null;
    }
}
