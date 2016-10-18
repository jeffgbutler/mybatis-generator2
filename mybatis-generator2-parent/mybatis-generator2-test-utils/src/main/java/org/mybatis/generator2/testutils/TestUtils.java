package org.mybatis.generator2.testutils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestUtils {

    public static final String USERID = "sa";
    public static final String JDBC_URL = "jdbc:hsqldb:mem:aname";
    public static final String JDBC_DRIVER = "org.hsqldb.jdbcDriver";

    private TestUtils() {
        // utility class - no instances
    }
    
    public static void createTestDatabase() {
        SqlScriptRunner scriptRunner = new SqlScriptRunner.Builder()
                .withSqlScript("classpath:org/mybatis/generator2/testutils/CreateDB.sql")
                .withJdbcDriver(JDBC_DRIVER)
                .withJdbcUrl(JDBC_URL)
                .withUserid(USERID)
                .build();
        
        scriptRunner.executeScript();
    }
    
    public static Connection getConnectionToTestDatabase() {
        try {
            Class.forName(JDBC_DRIVER);
            return DriverManager.getConnection(JDBC_URL, USERID, null);
        } catch (ClassNotFoundException | SQLException e) {
            throw new TestUtilsException("Exception getting test database connection", e);
        }
    }
}
