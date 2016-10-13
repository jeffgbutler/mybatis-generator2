package org.mybatis.generator2.tests.integration;

import static org.hamcrest.core.Is.*;
import static org.junit.Assert.assertThat;
import java.sql.Connection;
import java.sql.DatabaseMetaData;

import org.junit.Test;
import org.mybatis.generator2.config.Context;
import org.mybatis.generator2.config.TableConfiguration;
import org.mybatis.generator2.db.node.DatabaseIntrospector;
import org.mybatis.generator2.db.node.IntrospectionContext;
import org.mybatis.generator2.testutils.TestUtils;
import org.mybatis.generator2.util.JDBCUtils;

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

        Connection connection = null;

        try {
            connection = TestUtils.getConnection();
            DatabaseMetaData dbmd = connection.getMetaData();
            
            DatabaseIntrospector introspector = new DatabaseIntrospector.Builder()
                    .withContext(context)
                    .withDatabaseMetaData(dbmd)
                    .build();

            introspector.introspectTables();
            IntrospectionContext iContext = introspector.getIntrospectionContext();
            assertThat(iContext.getTables().count(), is(1L));

        } finally {
            JDBCUtils.close(connection);
        }
    }
}
