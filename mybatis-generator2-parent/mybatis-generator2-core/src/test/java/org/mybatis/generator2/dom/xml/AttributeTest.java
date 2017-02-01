package org.mybatis.generator2.dom.xml;

import static org.hamcrest.core.Is.*;
import static org.hamcrest.core.IsNot.*;
import static org.hamcrest.core.IsEqual.*;
import static org.junit.Assert.assertThat;
import org.junit.Test;

public class AttributeTest {

    @Test
    public void testGetters() {
        Attribute a1 = Attribute.of("resultType", "foo.Bar");
        assertThat(a1.name(), equalTo("resultType"));
        assertThat(a1.value(), equalTo("foo.Bar"));
    }

    @Test
    public void testEquals() {
        Attribute a1 = Attribute.of("resultType", "foo.Bar");
        Attribute a2 = Attribute.of("resultType", "foo.Bar");
        assertThat(a1, equalTo(a2));
    }

    @Test
    public void testEqualsWithSameObject() {
        Attribute a1 = Attribute.of("resultType", "foo.Bar");
        assertThat(a1, equalTo(a1));
    }

    @Test
    public void testEqualsWithNull1() {
        Attribute a1 = Attribute.of(null, "foo.Bar");
        Attribute a2 = Attribute.of("resultType", "foo.Bar");
        assertThat(a1, not(equalTo(a2)));
    }

    @Test
    public void testEqualsWithNull2() {
        Attribute a1 = Attribute.of("resultType", null);
        Attribute a2 = Attribute.of("resultType", "foo.Bar");
        assertThat(a1, not(equalTo(a2)));
    }

    @Test
    public void testEqualsWithNull3() {
        Attribute a1 = Attribute.of("resultType", "foo.Bar");
        Attribute a2 = Attribute.of(null, "foo.Bar");
        assertThat(a1, not(equalTo(a2)));
    }

    @Test
    public void testEqualsWithNull4() {
        Attribute a1 = Attribute.of("resultType", "foo.Bar");
        Attribute a2 = Attribute.of("resultType", null);
        assertThat(a1, not(equalTo(a2)));
    }

    @Test
    public void testEqualsWithNull5() {
        Attribute a1 = Attribute.of("resultType", "foo.Bar");
        assertThat(a1.equals(null), is(false));
    }

    @Test
    public void testEqualsWithNull6() {
        Attribute a1 = Attribute.of(null, null);
        Attribute a2 = Attribute.of(null, null);
        assertThat(a1, equalTo(a2));
    }

    @Test
    public void testEqualsWithDifferentObject() {
        Attribute a1 = Attribute.of("resultType", "foo.Bar");
        assertThat(a1, not(equalTo("fred")));
    }

    @Test
    public void testHashCode() {
        Attribute a1 = Attribute.of("resultType", "foo.Bar");
        Attribute a2 = Attribute.of("resultType", "foo.Bar");
        assertThat(a1.hashCode(), is(a2.hashCode()));
    }

    @Test
    public void testHashCodeWithNull1() {
        Attribute a1 = Attribute.of("resultType", "foo.Bar");
        Attribute a2 = Attribute.of(null, "foo.Bar");
        assertThat(a1.hashCode(), is(not(a2.hashCode())));
    }

    @Test
    public void testHashCodeWithNull2() {
        Attribute a1 = Attribute.of("resultType", "foo.Bar");
        Attribute a2 = Attribute.of("resultType", null);
        assertThat(a1.hashCode(), is(not(a2.hashCode())));
    }

    @Test
    public void testCompareToEquals() {
        Attribute a1 = Attribute.of("resultType", "foo.Bar");
        Attribute a2 = Attribute.of("resultType", "foo.Bar");
        assertThat(a1.compareTo(a2), is(0));
    }

    @Test
    public void testCompareToLessThan1() {
        Attribute a1 = Attribute.of("a", "c");
        Attribute a2 = Attribute.of("b", "c");
        assertThat(a1.compareTo(a2) < 0, is(true));
    }

    @Test
    public void testCompareToLessThan2() {
        Attribute a1 = Attribute.of("a", "c");
        Attribute a2 = Attribute.of("a", "d");
        assertThat(a1.compareTo(a2) < 0, is(true));
    }
}
