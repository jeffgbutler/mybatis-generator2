package org.mybatis.generator2.tests.integration;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.sql.Connection;
import java.sql.DatabaseMetaData;

import org.junit.Test;
import org.mybatis.generator2.config.Context;
import org.mybatis.generator2.config.TableConfiguration;
import org.mybatis.generator2.db.node.DatabaseIntrospector;
import org.mybatis.generator2.db.node.IntrospectionContext;
import org.mybatis.generator2.testutils.TestUtils;

public class IntrospectorIT {

    @Test
    public void testIntrospector() throws Exception {
        TestUtils.createTestDatabase();

        Context context = new Context();
        TableConfiguration configuration = new TableConfiguration.Builder()
                .withSchemaPattern("mbgtest")
                .withTableNamePattern("%")
                .build();

        context.addTable(configuration);

        try (Connection connection = TestUtils.getConnection()) {
            DatabaseMetaData dbmd = connection.getMetaData();
            
            DatabaseIntrospector introspector = DatabaseIntrospector.from(context, dbmd);

            introspector.introspectTables();
            IntrospectionContext iContext = introspector.getIntrospectionContext();
            assertThat(iContext.getTables().count(), is(1L));
        }
    }
}
