package org.mybatis.generator2.dom.xml;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class TextElementTest {

    @Test
    public void testGetters() {
        TextElement te = new TextElement("select * from foo");
        assertThat(te.getContent(), equalTo("select * from foo"));
    }

}
