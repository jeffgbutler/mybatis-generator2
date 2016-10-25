package org.mybatis.generator2.dom.xml;

public abstract class XmlDomNode {

    XmlDomNode parent;
    
    public XmlDomNode getParent() {
        return parent;
    }
    
    public abstract void accept(XmlDomVisitor visitor);
}
