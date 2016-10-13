package org.mybatis.generator2.testutils;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestUtils {

    private static final String USERID = "sa";
    private static final String JDBC_URL = "jdbc:hsqldb:mem:aname";
    private static final String JDBC_DRIVER = "org.hsqldb.jdbcDriver";

    public static void createTestDatabase() throws Exception {
        SqlScriptRunner scriptRunner = new SqlScriptRunner.Builder()
                .withSqlScript("classpath:org/mybatis/generator2/testutils/CreateDB.sql")
                .withJdbcDriver(JDBC_DRIVER)
                .withJdbcUrl(JDBC_URL)
                .withUserid(USERID)
                .build();
        
        scriptRunner.executeScript();
    }
    
    public static Connection getConnection() throws Exception {
        Class.forName(JDBC_DRIVER);
        return DriverManager.getConnection(JDBC_URL, USERID, null);
    }
}
