package org.mybatis.generator2.render.xml;

import static org.junit.Assert.assertThat;
import static org.mybatis.generator2.test_support.ResourceFileMatcher.matchesResourceFile;

import org.junit.Test;
import org.mybatis.generator2.dom.xml.Attribute;
import org.mybatis.generator2.dom.xml.Document;
import org.mybatis.generator2.dom.xml.DocumentTest;
import org.mybatis.generator2.dom.xml.XmlElement;
import org.mybatis.generator2.render.xml.DefaultXmlRenderer;

public class DefaultXmlRendererTest {

    @Test
    public void testFullDocument() {
        Document document = DocumentTest.setupDocument();
        
        DefaultXmlRenderer renderer = DefaultXmlRenderer.of(document);
        String content = renderer.render();
        assertThat(content, matchesResourceFile("/org/mybatis/generator2/dom/xml/FullDocument.xml"));

        Document d2 = document.deepCopy();
        renderer = DefaultXmlRenderer.of(d2);
        content = renderer.render();
        assertThat(content, matchesResourceFile("/org/mybatis/generator2/dom/xml/FullDocument.xml"));
    }

    @Test
    public void testEmptyDocument() {
        XmlElement rootElement = XmlElement.of("root");
        rootElement = rootElement.withAttribute(Attribute.of("foo", "bar"));
        Document document = Document.of(rootElement);

        DefaultXmlRenderer renderer = DefaultXmlRenderer.of(document);
        String content = renderer.render();
        assertThat(content, matchesResourceFile("/org/mybatis/generator2/dom/xml/EmptyDocument.xml"));
        
        Document d2 = document.deepCopy();
        renderer = DefaultXmlRenderer.of(d2);
        content = renderer.render();
        assertThat(content, matchesResourceFile("/org/mybatis/generator2/dom/xml/EmptyDocument.xml"));
    }
}
