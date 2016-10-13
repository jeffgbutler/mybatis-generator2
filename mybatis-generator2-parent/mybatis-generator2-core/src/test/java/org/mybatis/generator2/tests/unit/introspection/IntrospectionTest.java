package org.mybatis.generator2.tests.unit.introspection;

import static org.hamcrest.core.Is.*;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.mybatis.generator2.db.node.DatabaseIntrospector;

public class IntrospectionTest {

    @Test
    public void testDatabaseIntrospectorBuilder() {
        DatabaseIntrospector introspector = new DatabaseIntrospector.Builder()
                .build();
        assertThat(introspector, is(notNullValue()));
    }
}
