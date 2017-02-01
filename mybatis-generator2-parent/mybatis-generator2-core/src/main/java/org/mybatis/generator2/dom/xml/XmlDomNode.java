package org.mybatis.generator2.dom.xml;

public abstract class XmlDomNode<T> {

    XmlDomNode<?> parent;
    
    public XmlDomNode<?> parent() {
        return parent;
    }
    
    public abstract T deepCopy();
    
    public abstract <S> S accept(XmlDomVisitor<S> visitor);
}
