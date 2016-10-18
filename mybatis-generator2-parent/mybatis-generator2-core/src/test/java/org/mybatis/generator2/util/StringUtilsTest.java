package org.mybatis.generator2.util;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mybatis.generator2.util.StringUtils.stringContainsSQLWildcard;
import static org.mybatis.generator2.util.StringUtils.stringContainsSpace;
import static org.mybatis.generator2.util.StringUtils.stringHasValue;

import org.junit.Test;

public class StringUtilsTest {

    @Test
    public void testSpaces() {
        assertThat(stringContainsSpace("bamm bamm"), is(true));
    }

    @Test
    public void testNoSpaces() {
        assertThat(stringContainsSpace("Fred"), is(false));
    }

    @Test
    public void testNull() {
        assertThat(stringContainsSpace(null), is(false));
    }
    
    @Test
    public void testStringHasValue() {
        assertThat(stringHasValue("Fred"), is(true));
    }
    
    @Test
    public void testStringHasValueWithSpaces() {
        assertThat(stringHasValue("   "), is(false));
    }
    
    @Test
    public void testStringHasValueWithNull() {
        assertThat(stringHasValue(null), is(false));
    }
    
    @Test
    public void testStringHasWildCard1() {
        assertThat(stringContainsSQLWildcard("fred%"), is(true));
    }
    
    @Test
    public void testStringHasWildCard2() {
        assertThat(stringContainsSQLWildcard("_fred"), is(true));
    }
    
    @Test
    public void testStringHasWildCardWithNull() {
        assertThat(stringContainsSQLWildcard(null), is(false));
    }
    
    @Test
    public void testStringHasWildCardWithNoWildcardString() {
        assertThat(stringContainsSQLWildcard("Fred"), is(false));
    }
}
