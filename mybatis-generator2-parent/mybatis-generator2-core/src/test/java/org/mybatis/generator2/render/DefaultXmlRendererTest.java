package org.mybatis.generator2.render;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.mybatis.generator2.dom.xml.Document;
import org.mybatis.generator2.dom.xml.DocumentTest;
import org.mybatis.generator2.dom.xml.XmlElement;

public class DefaultXmlRendererTest {

    @Test
    public void testFullDocument() {
        Document document = DocumentTest.setupDocument();
        DefaultXmlRenderer renderer = new DefaultXmlRenderer();
        String content = renderer.render(document);
        
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
        XmlElement rootElement = new XmlElement("root");
        Document document = new Document(null, null, rootElement);
        DefaultXmlRenderer renderer = new DefaultXmlRenderer();
        String content = renderer.render(document);
        
        String targetContent =
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + System.lineSeparator() +
            "<!DOCTYPE root>" + System.lineSeparator() +
            "<root />";
        
        assertThat(content, is(targetContent));
    }
}
