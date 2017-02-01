package org.mybatis.generator2.render.xml;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.mybatis.generator2.dom.xml.Attribute;
import org.mybatis.generator2.dom.xml.Document;
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
                    .flatMap(c -> c.accept(this))
                    .map(this::indent)
                    .collect(Collectors.toList()));
            strings.add(renderEndTag(xmlElement));
            
            return strings;
        }

        private String renderXmlElementWithoutChildren(XmlElement xmlElement) {
            return String.format("<%s%s />", xmlElement.name(), renderAttributes(xmlElement.attributes())); //$NON-NLS-1$
        }

        private String indent(String s) {
            return "  " + s; //$NON-NLS-1$
        }
        
        private String renderStartTag(XmlElement xmlElement) {
            return String.format("<%s%s>", xmlElement.name(), renderAttributes(xmlElement.attributes())); //$NON-NLS-1$
        }
        
        private String renderEndTag(XmlElement xmlElement) {
            return String.format("</%s>", xmlElement.name()); //$NON-NLS-1$
        }

        private String renderAttributes(Stream<Attribute> attributes) {
            return attributes
                    .sorted()
                    .map(this::renderAttribute)
                    .collect(Collectors.joining(" ", " ", "")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        }

        private String renderAttribute(Attribute attribute) {
            return String.format("%s=\"%s\"", attribute.name(), attribute.value()); //$NON-NLS-1$
        }
    }
    
    private static class StringVisitor implements XmlDomVisitor<String> {
        @Override
        public String visit(Document document) {
            List<String> strings = new ArrayList<>();
            strings.add(XML_DECLARATION);
            strings.add(renderDocType(document));
            strings.addAll(document.rootElement().accept(new StreamVisitor()).collect(Collectors.toList()));
            
            return strings.stream().collect(Collectors.joining(System.lineSeparator()));
        }
        
        private String renderDocType(Document document) {
            StringBuilder buffer = new StringBuilder();
            buffer.append("<!DOCTYPE "); //$NON-NLS-1$
            buffer.append(document.rootElement().name());
            
            document.externalDTD().ifPresent(dtd -> {
                buffer.append(' ');
                buffer.append(dtd.accept(this));
            });

            buffer.append('>');
            return buffer.toString();
        }
        
        @Override
        public String visit(PublicExternalDTD dtd) {
            return String.format("PUBLIC \"%s\" \"%s\"", dtd.dtdName(), dtd.dtdLocation()); //$NON-NLS-1$
        }

        @Override
        public String visit(SystemExternalDTD dtd) {
            return String.format("SYSTEM \"%s\"", dtd.dtdLocation()); //$NON-NLS-1$
        }
    }
}
