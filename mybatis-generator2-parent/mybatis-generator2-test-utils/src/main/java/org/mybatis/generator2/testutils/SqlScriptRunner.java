/**
 *    Copyright 2006-2016 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mybatis.generator2.testutils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class is used to execute an SQL script before a code generation
 * run if necessary.  Note that this class mainly exists to support the
 * MyBatis generator build.  It is intentionally not documented and not
 * supported.
 * 
 * @author Jeff Butler
 */
public class SqlScriptRunner {
    private String jdbcDriver;
    private String jdbcUrl;
    private String userid;
    private String password;
    private String sqlScript;

    private SqlScriptRunner() {
    }

    public void executeScript() {
        loadDriver();
        
        try (Connection connection = DriverManager.getConnection(jdbcUrl, userid, password)) {
            executeScript(connection);
            connection.commit();
        } catch (SQLException | IOException e) {
            throw new TestUtilsException("Exception loading JDBC driver", e);
        }
    }
    
    private void loadDriver() {
        try {
            Class.forName(jdbcDriver);
        } catch (ClassNotFoundException e) {
            throw new TestUtilsException("Exception loading JDBC driver", e);
        }
    }
    
    private void executeScript(Connection connection) throws SQLException, IOException {
        try (Statement statement = connection.createStatement()) {
            executeScript(statement);
        }
    }
    
    private void executeScript(Statement statement) throws IOException, SQLException {
        try (BufferedReader br = getScriptReader()) {
            String sql;

            while ((sql = readStatement(br)) != null) {
                statement.execute(sql);
            }
        }
    }

    private String readStatement(BufferedReader br) throws IOException {
        StringBuilder sb = new StringBuilder();

        String line;

        while ((line = br.readLine()) != null) {
            if (line.length() == 0 || line.startsWith("--")) { //$NON-NLS-1$
                continue;
            }

            if (line.endsWith(";")) { //$NON-NLS-1$
                sb.append(line.substring(0, line.length() - 1));
                break;
            } else {
                sb.append(' ');
                sb.append(line);
            }
        }

        String s = sb.toString().trim();

        return s.length() > 0 ? s : null;
    }
    
    private BufferedReader getScriptReader() throws IOException {
        BufferedReader answer;
        
        if (sqlScript.startsWith("classpath:")) {
            String resource = sqlScript.substring("classpath:".length());
            InputStream is = 
                Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
            answer = new BufferedReader(new InputStreamReader(is));
        } else {
            File file = new File(sqlScript);
            answer = new BufferedReader(new FileReader(file));
        }
        
        return answer;
    }
    
    public static class Builder {
        private SqlScriptRunner runner = new SqlScriptRunner();
        
        public Builder withSqlScript(String sqlScript) {
            runner.sqlScript = sqlScript;
            return this;
        }
        
        public Builder withJdbcDriver(String jdbcDriver) {
            runner.jdbcDriver = jdbcDriver;
            return this;
        }
        
        public Builder withJdbcUrl(String jdbcUrl) {
            runner.jdbcUrl = jdbcUrl;
            return this;
        }
        
        public Builder withUserid(String userid) {
            runner.userid = userid;
            return this;
        }
        
        public Builder withPassword(String password) {
            runner.password = password;
            return this;
        }
        
        public SqlScriptRunner build() {
            return runner;
        }
    }
}
