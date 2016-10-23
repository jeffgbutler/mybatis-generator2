package org.mybatis.generator2.render;

import org.mybatis.generator2.dom.xml.Attribute;
import org.mybatis.generator2.dom.xml.Document;
import org.mybatis.generator2.dom.xml.TextElement;
import org.mybatis.generator2.dom.xml.XmlDomVisitor;
import org.mybatis.generator2.dom.xml.XmlElement;

public class DefaultXmlRenderer implements XmlDomVisitor, OutputUtilities {
    
    private StringBuilder buffer = new StringBuilder();
    private int indentLevel = 0;

    public DefaultXmlRenderer() {
        super();
    }
    
    public String render(Document document) {
        buffer.setLength(0);
        indentLevel = 0;
        document.accept(this);
        return buffer.toString();
    }

    @Override
    public boolean visit(Attribute attribute) {
        buffer.append(' ');
        buffer.append(attribute.getName());
        buffer.append("=\""); //$NON-NLS-1$
        buffer.append(attribute.getValue());
        buffer.append('\"');
        return true;
    }

    @Override
    public boolean visit(Document document) {
        buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"); //$NON-NLS-1$

        newLine(buffer);
        buffer.append("<!DOCTYPE "); //$NON-NLS-1$
        buffer.append(document.getRootElement().getName());
        
        if (document.getPublicId() != null && document.getSystemId() != null) {
            buffer.append(" PUBLIC \""); //$NON-NLS-1$
            buffer.append(document.getPublicId());
            buffer.append("\" \""); //$NON-NLS-1$
            buffer.append(document.getSystemId());
            buffer.append('\"');
        }
        buffer.append('>');

        return true;
    }

    @Override
    public boolean visit(TextElement textElement) {
        newLine(buffer);
        xmlIndent(buffer, indentLevel);
        buffer.append(textElement.getContent());
        return true;
    }

    @Override
    public boolean visit(XmlElement xmlElement) {

        if (xmlElement.hasChildElements()) {
            visitElementWithChildren(xmlElement);
        } else {
            visitElementWithoutChildren(xmlElement);
        }

        return false;
    }

    private void visitElementWithChildren(XmlElement xmlElement) {
        newLine(buffer);
        xmlIndent(buffer, indentLevel);
        buffer.append('<');
        buffer.append(xmlElement.getName());
        xmlElement.attributes().sorted().forEach(a -> a.accept(this));
        buffer.append('>');

        visitChildren(xmlElement);
        
        newLine(buffer);
        xmlIndent(buffer, indentLevel);
        buffer.append("</"); //$NON-NLS-1$
        buffer.append(xmlElement.getName());
        buffer.append('>');
    }

    private void visitChildren(XmlElement xmlElement) {
        indentLevel++;
        xmlElement.children().forEach(e -> e.accept(this));
        indentLevel--;
    }

    private void visitElementWithoutChildren(XmlElement xmlElement) {
        newLine(buffer);
        xmlIndent(buffer, indentLevel);
        buffer.append('<');
        buffer.append(xmlElement.getName());
        xmlElement.attributes().sorted().forEach(a -> a.accept(this));
        buffer.append(" />"); //$NON-NLS-1$
    }
}
