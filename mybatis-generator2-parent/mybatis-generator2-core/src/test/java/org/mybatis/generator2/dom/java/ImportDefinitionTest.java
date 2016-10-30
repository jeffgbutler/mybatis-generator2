package org.mybatis.generator2.dom.java;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.Test;
import org.mybatis.generator2.dom.java.JavaDomNode.JavaNodeType;

public class ImportDefinitionTest {

    @Test
    public void testGetters() {
        ImportDefinition importDefinition = ImportDefinition.of("org.junit.Assert", true, true);
        assertThat(importDefinition.getTypeName(), is("org.junit.Assert"));
        assertThat(importDefinition.isStatic(), is(true));
        assertThat(importDefinition.isWildcard(), is(true));
    }
    
    @Test
    public void testToString1() {
        ImportDefinition importDefinition = ImportDefinition.of("java.math.BigDecimal");
        assertThat(importDefinition.toString(), is("import java.math.BigDecimal;"));
        assertThat(importDefinition.isStatic(), is(false));
        assertThat(importDefinition.isNonStatic(), is(true));
        assertThat(importDefinition.isWildcard(), is(false));
        assertThat(importDefinition.getNodeType(), is(JavaNodeType.IMPORT));
        Arrays.stream(JavaModifier.values()).forEach(m -> {
            assertThat(importDefinition.allowsModifier(m), is(false));
        });
    }

    @Test
    public void testToString2() {
        ImportDefinition importDefinition = ImportDefinition.of("org.junit.Assert", true, true);
        assertThat(importDefinition.toString(), is("import static org.junit.Assert.*;"));
        assertThat(importDefinition.isStatic(), is(true));
        assertThat(importDefinition.isNonStatic(), is(false));
        assertThat(importDefinition.isWildcard(), is(true));
    }

    @Test
    public void testToString3() {
        ImportDefinition importDefinition = ImportDefinition.of("org.junit.Assert.assertThat", true, false);
        assertThat(importDefinition.toString(), is("import static org.junit.Assert.assertThat;"));
        assertThat(importDefinition.isStatic(), is(true));
        assertThat(importDefinition.isNonStatic(), is(false));
        assertThat(importDefinition.isWildcard(), is(false));
    }

    @Test
    public void testToString4() {
        ImportDefinition importDefinition = ImportDefinition.of("java.util", false, true);
        assertThat(importDefinition.toString(), is("import java.util.*;"));
        assertThat(importDefinition.isStatic(), is(false));
        assertThat(importDefinition.isNonStatic(), is(true));
        assertThat(importDefinition.isWildcard(), is(true));
    }

    @Test
    public void testToCompare1() {
        ImportDefinition importDefinition1 = ImportDefinition.of("java.math.BigDecimal");
        ImportDefinition importDefinition2 = ImportDefinition.of("java.math.BigInteger");
        assertThat(importDefinition1.compareTo(importDefinition2) < 0, is(true));
    }

    @Test
    public void testToCompare2() {
        ImportDefinition importDefinition1 = ImportDefinition.of("org.junit.Assert.assertThat", true, false);
        ImportDefinition importDefinition2 = ImportDefinition.of("java.math.BigInteger");
        assertThat(importDefinition1.compareTo(importDefinition2) < 0, is(true));
    }

    @Test
    public void testToCompare3() {
        ImportDefinition importDefinition1 = ImportDefinition.of("org.junit.Assert.assertThat", true, false);
        ImportDefinition importDefinition2 = ImportDefinition.of("java.math.BigInteger");
        assertThat(importDefinition2.compareTo(importDefinition1) > 0, is(true));
    }

    @Test
    public void testToCompare4() {
        ImportDefinition importDefinition1 = ImportDefinition.of("java.math.BigDecimal");
        ImportDefinition importDefinition2 = ImportDefinition.of("java.math.BigDecimal");
        assertThat(importDefinition1.compareTo(importDefinition2) == 0, is(true));
    }

    @Test
    public void testToEquals1() {
        ImportDefinition importDefinition1 = ImportDefinition.of("java.math.BigDecimal");
        ImportDefinition importDefinition2 = ImportDefinition.of("java.math.BigDecimal");
        assertThat(importDefinition1.equals(importDefinition2), is(true));
    }

    @Test
    public void testToEquals2() {
        ImportDefinition importDefinition1 = ImportDefinition.of("java.math.BigDecimal");
        ImportDefinition importDefinition2 = ImportDefinition.of("java.util.Map");
        assertThat(importDefinition1.equals(importDefinition2), is(false));
    }

    @Test
    public void testToEquals3() {
        ImportDefinition importDefinition1 = ImportDefinition.of("java.math.BigDecimal");
        assertThat(importDefinition1.equals(null), is(false));
    }

    @Test
    public void testToEquals4() {
        ImportDefinition importDefinition1 = ImportDefinition.of("java.math.BigDecimal");
        assertThat(importDefinition1.equals("fred"), is(false));
    }

    @Test
    public void testHashCode() {
        ImportDefinition importDefinition1 = ImportDefinition.of("java.math.BigDecimal");
        ImportDefinition importDefinition2 = ImportDefinition.of("java.math.BigDecimal");
        assertThat(importDefinition1.hashCode(), is(importDefinition2.hashCode()));
    }
}
