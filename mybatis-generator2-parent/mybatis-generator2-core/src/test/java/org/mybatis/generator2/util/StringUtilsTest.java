package org.mybatis.generator2.util;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.*;
import org.junit.Test;

public class StringUtilsTest {

    @Test
    public void testSpaces() {
        assertThat(StringUtils.stringContainsSpace("bamm bamm"), is(true));
    }

    @Test
    public void testNoSpaces() {
        assertThat(StringUtils.stringContainsSpace("Fred"), is(false));
    }

    @Test
    public void testNull() {
        assertThat(StringUtils.stringContainsSpace(null), is(false));
    }
    
    @Test
    public void testStringHasValue() {
        assertThat(StringUtils.stringHasValue("Fred"), is(true));
    }
    
    @Test
    public void testStringHasValueWithSpaces() {
        assertThat(StringUtils.stringHasValue("   "), is(false));
    }
    
    @Test
    public void testStringHasValueWithNull() {
        assertThat(StringUtils.stringHasValue(null), is(false));
    }
    
    @Test
    public void testStrangHasWildCard1() {
        assertThat(StringUtils.stringContainsSQLWildcard("fred%"), is(true));
    }
    
    @Test
    public void testStrangHasWildCard2() {
        assertThat(StringUtils.stringContainsSQLWildcard("_fred"), is(true));
    }
    
    @Test
    public void testStrangHasWildCardWithNull() {
        assertThat(StringUtils.stringContainsSQLWildcard(null), is(false));
    }
    
    @Test
    public void testStrangHasWildCardWithNoWildcardString() {
        assertThat(StringUtils.stringContainsSQLWildcard("Fred"), is(false));
    }
}
