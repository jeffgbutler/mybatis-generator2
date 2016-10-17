package org.mybatis.generator2.tests.integration;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import org.junit.Test;
import org.mybatis.generator2.config.Context;
import org.mybatis.generator2.config.TableConfiguration;
import org.mybatis.generator2.db.node.DatabaseIntrospector;
import org.mybatis.generator2.db.node.IntrospectionContext;
import org.mybatis.generator2.testutils.TestUtils;

public class IntrospectorIT {

    @Test
    public void testIntrospector() throws SQLException {
        TestUtils.createTestDatabase();

        Context context = new Context();
        TableConfiguration configuration = new TableConfiguration.Builder()
                .withSchemaPattern("mbgtest")
                .withTableNamePattern("%")
                .build();

        context.addTable(configuration);

        IntrospectionContext iContext;
        try (Connection connection = TestUtils.getConnectionToTestDatabase()) {
            DatabaseMetaData dbmd = connection.getMetaData();
            
            DatabaseIntrospector introspector = DatabaseIntrospector.from(context, dbmd);

            introspector.introspectTables();
            iContext = introspector.getIntrospectionContext();
        }

        assertThat(iContext.getTables().count(), is(1L));
        iContext.getTables().forEach(t -> {
            assertThat(t.hasAnyColumns(), is(true));
            assertThat(t.getAllColumns().count(), is(5L));
            assertThat(t.hasPrimaryKey(), is(true));
            assertThat(t.getPrimaryKeyColumns().count(), is(1L));
            assertThat(t.hasBlobColumns(), is(false));
            assertThat(t.hasBaseColumns(), is(true));
            assertThat(t.getBaseColumns().count(), is(4L));
        });
    }
}
