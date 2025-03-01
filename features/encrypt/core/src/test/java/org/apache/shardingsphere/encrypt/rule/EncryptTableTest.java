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

package org.apache.shardingsphere.encrypt.rule;

import org.apache.shardingsphere.encrypt.api.config.rule.EncryptColumnRuleConfiguration;
import org.apache.shardingsphere.encrypt.api.config.rule.EncryptTableRuleConfiguration;
import org.apache.shardingsphere.encrypt.exception.metadata.EncryptLogicColumnNotFoundException;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

public final class EncryptTableTest {
    
    private EncryptTable encryptTable;
    
    @Before
    public void setUp() {
        encryptTable = new EncryptTable(new EncryptTableRuleConfiguration("t_encrypt",
                Collections.singleton(new EncryptColumnRuleConfiguration("logicColumn", "cipherColumn", "assistedQueryColumn", "likeQueryColumn", "plainColumn", "myEncryptor", null)), null));
    }
    
    @Test
    public void assertFindEncryptorName() {
        assertTrue(encryptTable.findEncryptorName("logicColumn").isPresent());
    }
    
    @Test
    public void assertNotFindEncryptorName() {
        assertFalse(encryptTable.findEncryptorName("notExistLogicColumn").isPresent());
    }
    
    @Test
    public void assertGetLogicColumns() {
        assertThat(encryptTable.getLogicColumns(), is(Collections.singleton("logicColumn")));
    }
    
    @Test
    public void assertGetLogicColumnByCipherColumn() {
        assertNotNull(encryptTable.getLogicColumnByCipherColumn("cipherColumn"));
    }
    
    @Test
    public void assertGetLogicColumnByCipherColumnWhenNotFind() {
        assertThrows(EncryptLogicColumnNotFoundException.class, () -> encryptTable.getLogicColumnByCipherColumn("invalidColumn"));
    }
    
    @Test
    public void assertGetLogicColumnByPlainColumn() {
        assertNotNull(encryptTable.getLogicColumnByPlainColumn("plainColumn"));
    }
    
    @Test
    public void assertGetLogicColumnByPlainColumnWhenNotFind() {
        assertThrows(EncryptLogicColumnNotFoundException.class, () -> encryptTable.getLogicColumnByPlainColumn("invalidColumn"));
    }
    
    @Test
    public void assertIsCipherColumn() {
        assertTrue(encryptTable.isCipherColumn("CipherColumn"));
    }
    
    @Test
    public void assertIsNotCipherColumn() {
        assertFalse(encryptTable.isCipherColumn("logicColumn"));
    }
    
    @Test
    public void assertGetCipherColumn() {
        assertThat(encryptTable.getCipherColumn("LogicColumn"), is("cipherColumn"));
    }
    
    @Test
    public void assertGetAssistedQueryColumns() {
        assertThat(encryptTable.getAssistedQueryColumns(), is(Collections.singletonList("assistedQueryColumn")));
    }
    
    @Test
    public void assertFindAssistedQueryColumn() {
        Optional<String> actual = encryptTable.findAssistedQueryColumn("logicColumn");
        assertTrue(actual.isPresent());
        assertThat(actual.get(), is("assistedQueryColumn"));
    }
    
    @Test
    public void assertFindLikeQueryColumn() {
        Optional<String> actual = encryptTable.findLikeQueryColumn("logicColumn");
        assertTrue(actual.isPresent());
        assertThat(actual.get(), is("likeQueryColumn"));
    }
    
    @Test
    public void assertNotFindAssistedQueryColumn() {
        assertFalse(encryptTable.findAssistedQueryColumn("notExistLogicColumn").isPresent());
    }
    
    @Test
    public void assertNotFindLikeQueryColumn() {
        assertFalse(encryptTable.findAssistedQueryColumn("notExistLikeQueryColumn").isPresent());
    }
    
    @Test
    public void assertGetPlainColumns() {
        assertThat(encryptTable.getPlainColumns(), is(Collections.singletonList("plainColumn")));
    }
    
    @Test
    public void assertFindPlainColumn() {
        Optional<String> actual = encryptTable.findPlainColumn("logicColumn");
        assertTrue(actual.isPresent());
        assertThat(actual.get(), is("plainColumn"));
    }
    
    @Test
    public void assertNotFindPlainColumn() {
        assertFalse(encryptTable.findPlainColumn("notExistLogicColumn").isPresent());
    }
    
    @Test
    public void assertGetLogicAndCipherColumns() {
        assertThat(encryptTable.getLogicAndCipherColumns(), is(Collections.singletonMap("logicColumn", "cipherColumn")));
        assertTrue(encryptTable.getLogicAndCipherColumns().containsKey("LOGICCOLUMN"));
    }
    
    @Test
    public void assertGetQueryWithCipherColumn() {
        Optional<Boolean> actual = encryptTable.getQueryWithCipherColumn("logicColumn");
        assertFalse(actual.isPresent());
        
        encryptTable = new EncryptTable(new EncryptTableRuleConfiguration("t_encrypt",
                Collections.singleton(new EncryptColumnRuleConfiguration("logicColumn", "cipherColumn", "assistedQueryColumn", "likeQueryColumn", "plainColumn", "myEncryptor", null)), true));
        actual = encryptTable.getQueryWithCipherColumn("logicColumn");
        assertTrue(actual.isPresent());
        assertTrue(actual.get());
        
        encryptTable = new EncryptTable(new EncryptTableRuleConfiguration("t_encrypt",
                Collections.singleton(new EncryptColumnRuleConfiguration("logicColumn", "cipherColumn", "assistedQueryColumn", "likeQueryColumn", "plainColumn", "myEncryptor", false)), true));
        actual = encryptTable.getQueryWithCipherColumn("logicColumn");
        assertTrue(actual.isPresent());
        assertFalse(actual.get());
    }
}
