package org.mybatis.generator2.render;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

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
        
        String targetContent = 
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + System.lineSeparator() +
            "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">" + System.lineSeparator() +
            "<mapper namespace=\"foo.BarMapper\">" + System.lineSeparator() +
            "  <select id=\"selectBar\" resultType=\"foo.Bar\">" + System.lineSeparator() +
            "    select * from bar" + System.lineSeparator() +
            "    <if test=\"_parameter != null\">" + System.lineSeparator() +
            "      where foo = #{value}" + System.lineSeparator() +
            "    </if>" + System.lineSeparator() +
            "  </select>" + System.lineSeparator() +
            "</mapper>";
                
        assertThat(content, is(targetContent));
    }

    @Test
    public void testEmptyDocument() {
        XmlElement rootElement = XmlElement.of("root");
        rootElement = rootElement.withAttribute(Attribute.of("foo", "bar"));
        Document document = Document.of(rootElement);
        DefaultXmlRenderer renderer = DefaultXmlRenderer.of(document);
        String content = renderer.render();
        
        String targetContent =
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + System.lineSeparator() +
            "<!DOCTYPE root>" + System.lineSeparator() +
            "<root foo=\"bar\" />";
        
        assertThat(content, is(targetContent));
    }
}
