package org.mybatis.generator2.dom.xml;

public interface XmlDomNode {

    XmlDomNode getParent();
    void accept(XmlDomVisitor visitor);
}
