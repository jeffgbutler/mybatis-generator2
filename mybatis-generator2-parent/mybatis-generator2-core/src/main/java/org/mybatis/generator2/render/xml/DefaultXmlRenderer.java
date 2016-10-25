package org.mybatis.generator2.render.xml;

import org.mybatis.generator2.dom.xml.Attribute;
import org.mybatis.generator2.dom.xml.Document;
import org.mybatis.generator2.dom.xml.TextElement;
import org.mybatis.generator2.dom.xml.XmlDomVisitor;
import org.mybatis.generator2.dom.xml.XmlElement;
import org.mybatis.generator2.render.OutputUtilities;

public class DefaultXmlRenderer {

    private Document document;

    private DefaultXmlRenderer() {
        super();
    }

    public String render() {
        // we do this so the render method can be called
        // repeatedly.  This way we don't have to reset the
        // indentation level and buffer on each call.
        return new Renderer(document).render();
    }

    public static DefaultXmlRenderer of(Document document) {
        DefaultXmlRenderer defaultXmlRenderer = new DefaultXmlRenderer();
        defaultXmlRenderer.document = document;
        return defaultXmlRenderer;
    }

    private static class Renderer implements XmlDomVisitor, OutputUtilities {
        private StringBuilder buffer = new StringBuilder();
        private int indentLevel = 0;
        private Document document;
        
        public Renderer(Document document) {
            this.document = document;
        }
        
        public String render() {
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

            if (xmlElement.hasChildren()) {
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
}
