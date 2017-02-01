package org.mybatis.generator2.dom.xml;

public class SystemExternalDTD extends ExternalDTD<SystemExternalDTD> {

    private SystemExternalDTD(String dtdLocation) {
        super(dtdLocation);
    }

    @Override
    public SystemExternalDTD deepCopy() {
        return SystemExternalDTD.of(dtdLocation());
    }

    @Override
    public <S> S accept(XmlDomVisitor<S> visitor) {
        return visitor.visit(this);
    }

    public static SystemExternalDTD of(String dtdLocation) {
        return new SystemExternalDTD(dtdLocation);
    }
}
