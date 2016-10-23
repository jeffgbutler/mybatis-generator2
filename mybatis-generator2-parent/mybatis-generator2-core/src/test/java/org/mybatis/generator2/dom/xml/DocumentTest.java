package org.mybatis.generator2.dom.xml;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class DocumentTest {

    private static final String SYSTEM_ID = "http://mybatis.org/dtd/mybatis-3-mapper.dtd";
    private static final String PUBLIC_ID = "-//mybatis.org//DTD Mapper 3.0//EN";

    @Test
    public void testGetters() {
        Document document = setupDocument();
        assertThat(document.getPublicId(), is(PUBLIC_ID));
        assertThat(document.getSystemId(), is(SYSTEM_ID));
    }
    
    @Test
    public void testParentsAreSetCorrectly() {
        Document document = setupDocument();
        
        assertThat(document.getParent(), is(nullValue()));
        
        XmlElement root = document.getRootElement();
        assertThat(root.getParent(), is(instanceOf(Document.class)));
        
        root.attributes().forEach(a -> {
            assertThat(a.getParent(), is(root));
        });

        root.children().forEach(a -> {
            assertThat(a.getParent(), is(root));
        });
    }

    public static Document setupDocument() {
        XmlElement mapperElement = new XmlElement("mapper");
        mapperElement.addAttribute(new Attribute("namespace", "foo.BarMapper"));
        
        XmlElement selectElement = new XmlElement("select");
        selectElement.addAttribute(new Attribute("id", "selectBar"));
        selectElement.addAttribute(new Attribute("resultType", "foo.Bar"));
        
        selectElement.addElement(new TextElement("select * from bar"));
        
        XmlElement ifElement = new XmlElement("if");
        ifElement.addAttribute(new Attribute("test", "_parameter != null"));
        ifElement.addElement(new TextElement("where foo = #{value}"));
        selectElement.addElement(ifElement);
        mapperElement.addElement(selectElement);
        
        Document document = new Document(PUBLIC_ID, SYSTEM_ID, mapperElement);
        return document;
    }
}
