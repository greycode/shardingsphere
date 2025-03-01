/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shardingsphere.driver.jdbc.core.statement;

import org.apache.shardingsphere.driver.jdbc.base.AbstractShardingSphereDataSourceForShardingTest;
import org.apache.shardingsphere.infra.database.DefaultDatabase;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class ShardingSphereStatementTest extends AbstractShardingSphereDataSourceForShardingTest {
    
    @Test
    public void assertGetGeneratedKeys() throws SQLException {
        String sql = "INSERT INTO t_order_item(order_id, user_id, status) VALUES (%d, %d, '%s')";
        try (
                Connection connection = getShardingSphereDataSource().getConnection();
                Statement statement = connection.createStatement()) {
            assertFalse(statement.execute(String.format(sql, 1, 1, "init")));
            assertFalse(statement.execute(String.format(sql, 1, 1, "init"), Statement.NO_GENERATED_KEYS));
            assertFalse(statement.execute(String.format(sql, 1, 1, "init"), Statement.RETURN_GENERATED_KEYS));
            ResultSet generatedKeysResultSet = statement.getGeneratedKeys();
            assertTrue(generatedKeysResultSet.next());
            assertThat(generatedKeysResultSet.getLong(1), is(3L));
            assertFalse(statement.execute(String.format(sql, 1, 1, "init"), new int[]{1}));
            generatedKeysResultSet = statement.getGeneratedKeys();
            assertTrue(generatedKeysResultSet.next());
            assertThat(generatedKeysResultSet.getLong(1), is(4L));
            assertFalse(statement.execute(String.format(sql, 1, 1, "init"), new String[]{"user_id"}));
            generatedKeysResultSet = statement.getGeneratedKeys();
            assertTrue(generatedKeysResultSet.next());
            assertThat(generatedKeysResultSet.getLong(1), is(5L));
            assertFalse(statement.execute(String.format(sql, 1, 1, "init"), new int[]{2}));
            generatedKeysResultSet = statement.getGeneratedKeys();
            assertTrue(generatedKeysResultSet.next());
            assertThat(generatedKeysResultSet.getLong(1), is(6L));
            assertFalse(statement.execute(String.format(sql, 1, 1, "init"), new String[]{"status"}));
            generatedKeysResultSet = statement.getGeneratedKeys();
            assertTrue(generatedKeysResultSet.next());
            assertThat(generatedKeysResultSet.getLong(1), is(7L));
        }
    }
    
    @Test
    public void assertAddGetGeneratedKeysForNoGeneratedValues() throws SQLException {
        String sql = "INSERT INTO t_product (product_name) VALUES ('%s')";
        try (Statement statement = getShardingSphereDataSource().getConnection().createStatement()) {
            statement.execute(String.format(sql, "cup"), Statement.RETURN_GENERATED_KEYS);
            ResultSet generatedKeysResultSet = statement.getGeneratedKeys();
            assertTrue(generatedKeysResultSet.next());
            assertThat(generatedKeysResultSet.getInt(1), is(1));
        }
    }
    
    @Test
    public void assertQueryWithNull() throws SQLException {
        try (Statement statement = getShardingSphereDataSource().getConnection().createStatement()) {
            assertThrows(SQLException.class, () -> statement.executeQuery(null));
        }
    }
    
    @Test
    public void assertQueryWithEmptyString() throws SQLException {
        try (Statement statement = getShardingSphereDataSource().getConnection().createStatement()) {
            assertThrows(SQLException.class, () -> statement.executeQuery(""));
        }
    }
    
    @Test
    public void assertExecuteGetResultSet() throws SQLException {
        String sql = "UPDATE t_order_item SET status = '%s' WHERE user_id = %d AND order_id = %d";
        try (Statement statement = getShardingSphereDataSource().getConnection().createStatement()) {
            assertFalse(statement.execute(String.format(sql, "OK", 1, 1)));
            assertNull(statement.getResultSet());
        }
    }
    
    @Test
    public void assertExecuteUpdateGetResultSet() throws SQLException {
        String sql = "UPDATE t_order_item SET status = '%s' WHERE user_id = %d AND order_id = %d";
        try (Statement statement = getShardingSphereDataSource().getConnection().createStatement()) {
            statement.executeUpdate(String.format(sql, "OK", 1, 1));
            assertNull(statement.getResultSet());
        }
    }
    
    @Test
    public void assertColumnNotFoundException() throws SQLException {
        String sql = "UPDATE t_order_item SET error_column = '%s'";
        try (Statement statement = getShardingSphereDataSource().getConnection().createStatement()) {
            assertThrows(SQLException.class, () -> statement.executeUpdate(String.format(sql, "OK")));
        }
    }
    
    @Test
    public void assertShowDatabases() throws SQLException {
        String sql = "SHOW DATABASES";
        try (Statement statement = getShardingSphereDataSource().getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            assertTrue(resultSet.next());
            assertThat(resultSet.getString(1), is(DefaultDatabase.LOGIC_NAME));
        }
    }
}
