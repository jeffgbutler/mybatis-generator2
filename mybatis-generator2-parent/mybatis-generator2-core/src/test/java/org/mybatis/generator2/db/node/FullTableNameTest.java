package org.mybatis.generator2.db.node;

import static org.hamcrest.core.IsNot.*;
import static org.hamcrest.core.Is.*;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class FullTableNameTest {

    @Test
    public void testTableOnly() {
        FullTableName fullTableName = FullTableName.from(null, null, "table1", null);
        assertThat(fullTableName.toString(), is("table1"));
        assertThat(fullTableName.getCatalog(), is(nullValue()));
        assertThat(fullTableName.getSchema(), is(nullValue()));
        assertThat(fullTableName.getTableName(), is("table1"));
        assertThat(fullTableName.getRemarks(), is(nullValue()));
    }

    @Test
    public void testTableAndSchema() {
        FullTableName fullTableName = FullTableName.from(null, "schema1", "table1", null);
        assertThat(fullTableName.toString(), is("schema1.table1"));
        assertThat(fullTableName.getCatalog(), is(nullValue()));
        assertThat(fullTableName.getSchema(), is("schema1"));
        assertThat(fullTableName.getTableName(), is("table1"));
        assertThat(fullTableName.getRemarks(), is(nullValue()));
    }

    @Test
    public void testTableAndCatalog() {
        FullTableName fullTableName = FullTableName.from("catalog1", null, "table1", null);
        assertThat(fullTableName.toString(), is("catalog1..table1"));
        assertThat(fullTableName.getCatalog(), is("catalog1"));
        assertThat(fullTableName.getSchema(), is(nullValue()));
        assertThat(fullTableName.getTableName(), is("table1"));
        assertThat(fullTableName.getRemarks(), is(nullValue()));
    }

    @Test
    public void testAll() {
        FullTableName fullTableName = FullTableName.from("catalog1", "schema1", "table1", "some remarks");
        assertThat(fullTableName.toString(), is("catalog1.schema1.table1"));
        assertThat(fullTableName.getCatalog(), is("catalog1"));
        assertThat(fullTableName.getSchema(), is("schema1"));
        assertThat(fullTableName.getTableName(), is("table1"));
        assertThat(fullTableName.getRemarks(), is("some remarks"));
    }

    @Test
    public void testEquals() {
        FullTableName fullTableName1 = FullTableName.from(null, "schema1", "table1", null);
        FullTableName fullTableName2 = FullTableName.from(null, "schema1", "table1", null);
        assertThat(fullTableName1, is(equalTo(fullTableName2)));
    }

    @Test
    public void testEqualsWithNull() {
        FullTableName fullTableName1 = FullTableName.from(null, "schema1", "table1", null);
        assertThat(fullTableName1, is(not(equalTo(nullValue()))));
    }

    @Test
    public void testEqualsWithString() {
        FullTableName fullTableName1 = FullTableName.from(null, "schema1", "table1", null);
        assertThat(fullTableName1, is(not(equalTo("fred"))));
    }
}
