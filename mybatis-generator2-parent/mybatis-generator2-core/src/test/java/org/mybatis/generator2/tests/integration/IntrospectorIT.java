package org.mybatis.generator2.tests.integration;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.mybatis.generator2.config.Context;
import org.mybatis.generator2.config.JDBCConnectionFactory;
import org.mybatis.generator2.config.TableConfiguration;
import org.mybatis.generator2.exception.IntrospectionException;
import org.mybatis.generator2.introspect.IntrospectedContext;
import org.mybatis.generator2.testutils.TestUtils;

public class IntrospectorIT {

    private static Map<String, long[]> TABLE_INFO = new HashMap<>();
    
    static {
        TABLE_INFO.put("PUBLIC.MBGTEST.ANOTHERAWFULTABLE", new long[] {1, 4, 0});
        TABLE_INFO.put("PUBLIC.PUBLIC.FIELDSONLY", new long[] {0, 3, 0});
        TABLE_INFO.put("PUBLIC.PUBLIC.PKONLY", new long[] {2, 0, 0});
        TABLE_INFO.put("PUBLIC.PUBLIC.PKFIELDS", new long[] {2, 12, 0});
        TABLE_INFO.put("PUBLIC.PUBLIC.PKBLOBS", new long[] {1, 0, 3});
        TABLE_INFO.put("PUBLIC.PUBLIC.PKFIELDSBLOBS", new long[] {2, 2, 1});
        TABLE_INFO.put("PUBLIC.PUBLIC.FIELDSBLOBS", new long[] {0, 2, 3});
        TABLE_INFO.put("PUBLIC.PUBLIC.awful table", new long[] {1, 16, 0});
        TABLE_INFO.put("PUBLIC.PUBLIC.BLOBSONLY", new long[] {0, 0, 2});
        TABLE_INFO.put("PUBLIC.PUBLIC.REGEXRENAME", new long[] {1, 3, 0});
        TABLE_INFO.put("PUBLIC.PUBLIC.NAMEVIEW", new long[] {0, 2, 0});
        TABLE_INFO.put("PUBLIC.PUBLIC.COMPOUNDKEY", new long[] {2, 1, 0});
        TABLE_INFO.put("PUBLIC.PUBLIC.ENUMTEST", new long[] {1, 1, 0});
        TABLE_INFO.put("PUBLIC.PUBLIC.GENERATEDALWAYSTEST", new long[] {1, 3, 1});
        TABLE_INFO.put("PUBLIC.PUBLIC.GENERATEDALWAYSTESTNOUPDATES", new long[] {1, 2, 0});
        TABLE_INFO.put("PUBLIC.PUBLIC.IGNOREMANYCOLUMNS", new long[] {1, 14, 0});
        TABLE_INFO.put("PUBLIC.PUBLIC.table with wildcard%", new long[] {1, 1, 0});
    }
    
    @Test
    public void testIntrospector() throws IntrospectionException {
        TestUtils.createTestDatabase();

        Context context = new Context();
        TableConfiguration configuration = new TableConfiguration.Builder()
                .withSchemaPattern("mbgtest")
                .withTableNamePattern("%")
                .build();

        context.addTable(configuration);
        context.addTable(TableConfiguration.from("FieldsOnly"));
        context.addTable(TableConfiguration.from("PKOnly"));
        context.addTable(TableConfiguration.from("PKFields"));
        context.addTable(TableConfiguration.from("PKBlobs"));
        context.addTable(TableConfiguration.from("PKFieldsBlobs"));
        context.addTable(TableConfiguration.from("FieldsBlobs"));
        context.addTable(TableConfiguration.from("awful table"));
        context.addTable(TableConfiguration.from("BlobsOnly"));
        context.addTable(TableConfiguration.from("RegexRename"));
        context.addTable(TableConfiguration.from("NameView"));
        context.addTable(TableConfiguration.from("CompoundKey"));
        context.addTable(TableConfiguration.from("EnumTest"));
        context.addTable(TableConfiguration.from("GeneratedAlwaysTest"));
        context.addTable(TableConfiguration.from("GeneratedAlwaysTestNoUpdates"));
        context.addTable(TableConfiguration.from("IgnoreManyColumns"));

        configuration = new TableConfiguration.Builder()
                .withTableNamePattern("table with wildcard%")
                .withWildcardEscapingEnabled(true)
                .build();
        context.addTable(configuration);
        
        JDBCConnectionFactory factory = JDBCConnectionFactory.from(TestUtils.JDBC_DRIVER, TestUtils.JDBC_URL, TestUtils.USERID);
        context.setConnectionFactory(factory);

        IntrospectedContext iContext = IntrospectedContext.from(context);

        assertThat(iContext.getConfigs().count(), is(17L));
        iContext.getConfigs().forEach(c -> c.tables().forEach(t -> {
            long[] columnCounts = TABLE_INFO.get(t.getFullTableName().toString());
            
            if (columnCounts[0] > 0) {
                assertThat(t.hasPrimaryKey(), is(true));
                assertThat(t.primaryKeyColumns().count(), is(columnCounts[0]));
            } else {
                assertThat(t.hasPrimaryKey(), is(false));
                assertThat(t.primaryKeyColumns().count(), is(0L));
            }
            
            if (columnCounts[1] > 0) {
                assertThat(t.hasBaseColumns(), is(true));
                assertThat(t.baseColumns().count(), is(columnCounts[1]));
            } else {
                assertThat(t.hasBaseColumns(), is(false));
                assertThat(t.baseColumns().count(), is(0L));
            }
            
            if (columnCounts[2] > 0) {
                assertThat(t.hasBlobColumns(), is(true));
                assertThat(t.blobColumns().count(), is(columnCounts[2]));
            } else {
                assertThat(t.hasBlobColumns(), is(false));
                assertThat(t.blobColumns().count(), is(0L));
            }
            
            assertThat(t.hasAnyColumns(), is(true));
            assertThat(t.allColumns().count(), is(Arrays.stream(columnCounts).sum()));
        }));
    }
}
