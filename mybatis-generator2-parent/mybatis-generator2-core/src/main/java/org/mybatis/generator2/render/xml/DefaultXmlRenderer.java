package org.mybatis.generator2.render.xml;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.mybatis.generator2.dom.xml.AbstractElement;
import org.mybatis.generator2.dom.xml.Attribute;
import org.mybatis.generator2.dom.xml.Document;
import org.mybatis.generator2.dom.xml.ExternalDTD;
import org.mybatis.generator2.dom.xml.PublicExternalDTD;
import org.mybatis.generator2.dom.xml.SystemExternalDTD;
import org.mybatis.generator2.dom.xml.TextElement;
import org.mybatis.generator2.dom.xml.XmlElement;
import org.mybatis.generator2.dom.xml.XmlDomVisitor;

public class DefaultXmlRenderer {

    private static final String XML_DECLARATION = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"; //$NON-NLS-1$
    private Document document;

    private DefaultXmlRenderer() {
        super();
    }

    public String render() {
        return document.accept(new StringVisitor());
    }

    public static DefaultXmlRenderer of(Document document) {
        DefaultXmlRenderer defaultXmlRenderer = new DefaultXmlRenderer();
        defaultXmlRenderer.document = document;
        return defaultXmlRenderer;
    }
    
    private static class StreamVisitor implements XmlDomVisitor<Stream<String>> {
        @Override
        public Stream<String> visit(XmlElement xmlElement) {
            if (xmlElement.hasChildren()) {
                return renderXmlElementWithChildren(xmlElement).stream();
            } else {
                return Stream.of(renderXmlElementWithoutChildren(xmlElement));
            }
        }
        
        @Override
        public Stream<String> visit(TextElement textElement) {
            return Stream.of(textElement.content());
        }

        private List<String> renderXmlElementWithChildren(XmlElement xmlElement) {
            List<String> strings = new ArrayList<>();

            strings.add(renderStartTag(xmlElement));
            strings.addAll(
                    xmlElement.children()
                    .flatMap(this::renderChildElement)
                    .map(this::indent)
                    .collect(Collectors.toList()));
            strings.add(renderEndTag(xmlElement));
            
            return strings;
        }
        
        private Stream<String> renderChildElement(AbstractElement<?> abstractElement) {
            return abstractElement.accept(this);
        }

        private String renderXmlElementWithoutChildren(XmlElement xmlElement) {
            return "<" //$NON-NLS-1$
                    + xmlElement.name()
                    + renderAttributes(xmlElement.attributes())
                    + " />"; //$NON-NLS-1$
        }

        private String indent(String s) {
            return "  " + s; //$NON-NLS-1$
        }
        
        private String renderStartTag(XmlElement xmlElement) {
            return "<" //$NON-NLS-1$
                    + xmlElement.name()
                    + renderAttributes(xmlElement.attributes())
                    + ">"; //$NON-NLS-1$
        }
        
        private String renderEndTag(XmlElement xmlElement) {
            return "</" //$NON-NLS-1$
                    + xmlElement.name()
                    + ">"; //$NON-NLS-1$
        }

        private String renderAttributes(Stream<Attribute> attributes) {
            return attributes
                    .sorted()
                    .map(this::renderAttribute)
                    .collect(Collectors.joining(" ", " ", "")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        }

        private String renderAttribute(Attribute attribute) {
            return attribute.name()
                    + "=\"" //$NON-NLS-1$
                    + attribute.value()
                    + "\""; //$NON-NLS-1$
        }
    }
    
    private static class StringVisitor implements XmlDomVisitor<String> {
        @Override
        public String visit(Document document) {
            return XML_DECLARATION
                    + System.lineSeparator()
                    + renderDocType(document)
                    + System.lineSeparator()
                    + document.rootElement().accept(new StreamVisitor())
                    .collect(Collectors.joining(System.lineSeparator()));
        }
        
        private String renderDocType(Document document) {
            StringJoiner sj = new StringJoiner("", "<!DOCTYPE ", ">"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
            sj.add(document.rootElement().name());
            sj.add(document.externalDTD().map(this::renderExternalDTD).orElse("")); //$NON-NLS-1$
            return sj.toString();
        }
        
        private String renderExternalDTD(ExternalDTD<?> externalDTD) {
            return externalDTD.accept(this);
        }
        
        @Override
        public String visit(PublicExternalDTD dtd) {
            return " PUBLIC \"" //$NON-NLS-1$
                    + dtd.dtdName()
                    + "\" \"" //$NON-NLS-1$
                    + dtd.dtdLocation()
                    + "\""; //$NON-NLS-1$
        }

        @Override
        public String visit(SystemExternalDTD dtd) {
            return " SYSTEM \"" //$NON-NLS-1$
                    + dtd.dtdLocation()
                    + "\""; //$NON-NLS-1$
        }
    }
}
