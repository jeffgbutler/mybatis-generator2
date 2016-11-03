package org.mybatis.generator2.introspect;

import static org.hamcrest.core.IsNot.*;
import static org.hamcrest.core.Is.*;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.mybatis.generator2.introspect.FullTableName;

public class FullTableNameTest {

    @Test
    public void testTableOnly() {
        FullTableName fullTableName = new FullTableName.Builder()
                .withTableName("table1")
                .build();
        assertThat(fullTableName.toString(), is("table1"));
        assertThat(fullTableName.getCatalog(), is(nullValue()));
        assertThat(fullTableName.getSchema(), is(nullValue()));
        assertThat(fullTableName.getTableName(), is("table1"));
        assertThat(fullTableName.getRemarks(), is(nullValue()));
    }

    @Test
    public void testTableAndSchema() {
        FullTableName fullTableName = new FullTableName.Builder()
                .withSchema("schema1")
                .withTableName("table1")
                .build();
        assertThat(fullTableName.toString(), is("schema1.table1"));
        assertThat(fullTableName.getCatalog(), is(nullValue()));
        assertThat(fullTableName.getSchema(), is("schema1"));
        assertThat(fullTableName.getTableName(), is("table1"));
        assertThat(fullTableName.getRemarks(), is(nullValue()));
    }

    @Test
    public void testTableAndCatalog() {
        FullTableName fullTableName = new FullTableName.Builder()
                .withCatalog("catalog1")
                .withTableName("table1")
                .build();
        assertThat(fullTableName.toString(), is("catalog1..table1"));
        assertThat(fullTableName.getCatalog(), is("catalog1"));
        assertThat(fullTableName.getSchema(), is(nullValue()));
        assertThat(fullTableName.getTableName(), is("table1"));
        assertThat(fullTableName.getRemarks(), is(nullValue()));
    }

    @Test
    public void testAll() {
        FullTableName fullTableName = new FullTableName.Builder()
                .withCatalog("catalog1")
                .withSchema("schema1")
                .withTableName("table1")
                .withRemarks("some remarks")
                .build();
        assertThat(fullTableName.toString(), is("catalog1.schema1.table1"));
        assertThat(fullTableName.getCatalog(), is("catalog1"));
        assertThat(fullTableName.getSchema(), is("schema1"));
        assertThat(fullTableName.getTableName(), is("table1"));
        assertThat(fullTableName.getRemarks(), is("some remarks"));
    }

    @Test
    public void testEquals() {
        FullTableName fullTableName1 = new FullTableName.Builder()
                .withSchema("schema1")
                .withTableName("table1")
                .build();
        FullTableName fullTableName2 = new FullTableName.Builder()
                .withSchema("schema1")
                .withTableName("table1")
                .build();
        assertThat(fullTableName1, is(equalTo(fullTableName2)));
    }

    @Test
    public void testHashCode() {
        FullTableName fullTableName1 = new FullTableName.Builder()
                .withSchema("schema1")
                .withTableName("table1")
                .build();
        FullTableName fullTableName2 = new FullTableName.Builder()
                .withSchema("schema1")
                .withTableName("table1")
                .build();
        assertThat(fullTableName1.hashCode(), is(equalTo(fullTableName2.hashCode())));
    }

    @Test
    public void testEqualsWithNull() {
        FullTableName fullTableName1 = new FullTableName.Builder()
                .withSchema("schema1")
                .withTableName("table1")
                .build();
        assertThat(fullTableName1.equals(null), is(false));
    }

    @Test
    public void testEqualsWithString() {
        FullTableName fullTableName1 = new FullTableName.Builder()
                .withSchema("schema1")
                .withTableName("table1")
                .build();
        assertThat(fullTableName1, is(not(equalTo("fred"))));
    }
}
