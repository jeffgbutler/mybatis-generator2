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
        assertThat(document.externalDTD().isPresent(), is(true));
        
        document.externalDTD().ifPresent(dtd -> {
            assertThat(dtd, is(instanceOf(PublicExternalDTD.class)));
            assertThat(((PublicExternalDTD) dtd).dtdName(), is(PUBLIC_ID));
            assertThat(dtd.dtdLocation(), is(SYSTEM_ID));
        });
    }
    
    @Test
    public void testParentsAreSetCorrectly() {
        Document document = setupDocument();
        
        assertThat(document.parent(), is(nullValue()));
        
        XmlElement root = document.rootElement();
        assertThat(root.parent(), is(instanceOf(Document.class)));
        
        root.attributes().forEach(a -> {
            assertThat(a.parent(), is(root));
        });

        root.children().forEach(a -> {
            assertThat(a.parent(), is(root));
        });
    }

    public static Document setupDocument() {
        
        XmlElement mapperElement = new XmlElement.Builder()
                .withName("mapper")
                .build();
        
        mapperElement = mapperElement.withAttribute(Attribute.of("namespace", "foo.BarMapper"));

        XmlElement ifElement = new XmlElement.Builder()
                .withName("if")
                .withAttribute(Attribute.of("test", "_parameter != null"))
                .withChild(TextElement.of("where foo = #{value}"))
                .build();

        XmlElement selectElement = new XmlElement.Builder()
                .withName("select")
                .withAttribute(Attribute.of("id", "selectBar"))
                .withAttribute(Attribute.of("resultType", "foo.Bar"))
                .withChild(TextElement.of("select * from bar"))
                .withChild(ifElement)
                .build();

        mapperElement = mapperElement.withChild(selectElement);
        
        PublicExternalDTD dtd = PublicExternalDTD.of(PUBLIC_ID, SYSTEM_ID);
        
        Document document = Document.of(dtd, mapperElement);
        return document;
    }
}
