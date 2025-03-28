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

package org.apache.shardingsphere.test.e2e.transaction.cases.autocommit;

import org.apache.shardingsphere.data.pipeline.core.util.ThreadUtil;
import org.apache.shardingsphere.test.e2e.transaction.cases.base.BaseTransactionTestCase;
import org.apache.shardingsphere.test.e2e.transaction.engine.base.TransactionBaseE2EIT;
import org.apache.shardingsphere.test.e2e.transaction.engine.base.TransactionTestCase;
import org.apache.shardingsphere.test.e2e.transaction.engine.constants.TransactionTestConstants;
import org.apache.shardingsphere.transaction.api.TransactionType;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * PostgresSQL auto commit transaction integration test.
 */
@TransactionTestCase(dbTypes = {TransactionTestConstants.POSTGRESQL}, transactionTypes = TransactionType.LOCAL)
public final class PostgresSQLAutoCommitTestCase extends BaseTransactionTestCase {
    
    public PostgresSQLAutoCommitTestCase(final TransactionBaseE2EIT baseTransactionITCase, final DataSource dataSource) {
        super(baseTransactionITCase, dataSource);
    }
    
    @Override
    public void executeTest() throws SQLException {
        assertAutoCommit();
    }
    
    private void assertAutoCommit() throws SQLException {
        Connection connection1 = getDataSource().getConnection();
        Connection connection2 = getDataSource().getConnection();
        executeWithLog(connection1, "set transaction isolation level read committed;");
        executeWithLog(connection2, "set transaction isolation level read committed;");
        connection1.setAutoCommit(false);
        connection2.setAutoCommit(false);
        executeWithLog(connection1, "insert into account(id, balance, transaction_id) values(1, 100, 1);");
        assertFalse(executeQueryWithLog(connection2, "select * from account;").next());
        connection1.commit();
        ThreadUtil.sleep(1, TimeUnit.SECONDS);
        assertTrue(executeQueryWithLog(connection2, "select * from account;").next());
        connection1.close();
        connection2.close();
    }
}
