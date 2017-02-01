package org.mybatis.generator2.dom.xml;

public class PublicExternalDTD extends ExternalDTD<PublicExternalDTD> {

    private String dtdName;
    
    private PublicExternalDTD(String dtdName, String dtdLocation) {
        super(dtdLocation);
        this.dtdName = dtdName;
    }
    
    public String dtdName() {
        return dtdName;
    }

    @Override
    public PublicExternalDTD deepCopy() {
        return PublicExternalDTD.of(dtdName, dtdLocation());
    }
    
    @Override
    public <S> S accept(XmlDomVisitor<S> visitor) {
        return visitor.visit(this);
    }

    public static PublicExternalDTD of(String dtdName, String dtdLocation) {
        return new PublicExternalDTD(dtdName, dtdLocation);
    }
}
