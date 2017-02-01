package org.mybatis.generator2.dom.xml;

public abstract class ExternalDTD<T extends ExternalDTD<T>> extends XmlDomNode<T>{

    private String dtdLocation;
    
    protected ExternalDTD(String dtdLocation) {
        this.dtdLocation = dtdLocation;
    }
    
    public String dtdLocation() {
        return dtdLocation;
    }
}
