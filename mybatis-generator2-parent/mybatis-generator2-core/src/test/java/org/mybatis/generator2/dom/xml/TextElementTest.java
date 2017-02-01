package org.mybatis.generator2.dom.xml;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class TextElementTest {

    @Test
    public void testGetters() {
        TextElement te = TextElement.of("select * from foo");
        assertThat(te.content(), equalTo("select * from foo"));
        assertThat(te.parent(), is(nullValue()));
    }
}
