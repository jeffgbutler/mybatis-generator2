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
package org.mybatis.generator2.config;

import static org.mybatis.generator2.util.StringUtils.stringHasValue;
import static org.mybatis.generator2.util.Messages.getString;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import org.mybatis.generator2.exception.IntrospectionException;
import org.mybatis.generator2.util.Messages.MessageId;
import org.mybatis.generator2.util.ObjectFactory;

/**
 * 
 * @author Jeff Butler
 */
public class JDBCConnectionFactory implements ConnectionFactory {

    private String driverClass;
    private String connectionURL;
    private String userId;
    private String password;

    private JDBCConnectionFactory() {
        super();
    }

    public void validate(List<String> errors) {
        if (!stringHasValue(driverClass)) {
            errors.add(getString(MessageId.VALIDATION_ERROR_4));
        }

        if (!stringHasValue(connectionURL)) {
            errors.add(getString(MessageId.VALIDATION_ERROR_5));
        }
    }

    @Override
    public Connection getConnection() throws IntrospectionException {
        try {
            ObjectFactory.externalClassForName(driverClass);
            return DriverManager.getConnection(connectionURL, userId, password);
        } catch (ClassNotFoundException | SQLException e) {
            throw new IntrospectionException("Error creating database connection", e);
        }
    }

    public static JDBCConnectionFactory from(String driverClass, String connectionURL, String userId, String password) {
        JDBCConnectionFactory factory = new JDBCConnectionFactory();
        factory.driverClass = driverClass;
        factory.connectionURL = connectionURL;
        factory.userId = userId;
        factory.password = password;
        return factory;
    }

    public static JDBCConnectionFactory from(String driverClass, String connectionURL, String userId) {
        JDBCConnectionFactory factory = new JDBCConnectionFactory();
        factory.driverClass = driverClass;
        factory.connectionURL = connectionURL;
        factory.userId = userId;
        return factory;
    }
}
