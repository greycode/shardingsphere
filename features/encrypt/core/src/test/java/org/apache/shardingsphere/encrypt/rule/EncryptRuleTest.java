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

import org.apache.shardingsphere.encrypt.api.config.EncryptRuleConfiguration;
import org.apache.shardingsphere.encrypt.api.config.rule.EncryptColumnRuleConfiguration;
import org.apache.shardingsphere.encrypt.api.config.rule.EncryptTableRuleConfiguration;
import org.apache.shardingsphere.infra.config.algorithm.AlgorithmConfiguration;
import org.apache.shardingsphere.infra.database.DefaultDatabase;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

public final class EncryptRuleTest {
    
    @Test
    public void assertFindEncryptTable() {
        assertTrue(new EncryptRule(createEncryptRuleConfiguration()).findEncryptTable("t_encrypt").isPresent());
    }
    
    @Test
    public void assertFindEncryptor() {
        assertTrue(new EncryptRule(createEncryptRuleConfiguration()).findEncryptor("t_encrypt", "pwd").isPresent());
    }
    
    @Test
    public void assertNotFindEncryptor() {
        assertFalse(new EncryptRule(createEncryptRuleConfiguration()).findEncryptor("t_encrypt", "other_column").isPresent());
    }
    
    @Test
    public void assertGetEncryptValues() {
        List<Object> encryptAssistedQueryValues = new EncryptRule(createEncryptRuleConfiguration())
                .getEncryptValues(DefaultDatabase.LOGIC_NAME, DefaultDatabase.LOGIC_NAME, "t_encrypt", "pwd", Collections.singletonList(null));
        for (Object each : encryptAssistedQueryValues) {
            assertNull(each);
        }
    }
    
    @Test
    public void assertGetCipherColumnWhenEncryptColumnExist() {
        assertThat(new EncryptRule(createEncryptRuleConfiguration()).getCipherColumn("t_encrypt", "pwd"), is("pwd_cipher"));
    }
    
    @Test
    public void assertGetCipherColumnWhenNoEncryptColumn() {
        // TODO should not throw NPE
        assertThrows(NullPointerException.class, () -> new EncryptRule(createEncryptRuleConfiguration()).getCipherColumn("t_encrypt", "pwd_cipher"));
    }
    
    @Test
    public void assertGetLogicAndCipherColumns() {
        assertFalse(new EncryptRule(createEncryptRuleConfiguration()).getLogicAndCipherColumns("t_encrypt").isEmpty());
    }
    
    @Test
    public void assertFindAssistedQueryColumn() {
        assertFalse(new EncryptRule(createEncryptRuleConfiguration()).findAssistedQueryColumn("t_encrypt", "pwd_cipher").isPresent());
    }
    
    @Test
    public void assertGetEncryptAssistedQueryValues() {
        List<Object> encryptAssistedQueryValues = new EncryptRule(createEncryptRuleConfiguration())
                .getEncryptAssistedQueryValues(DefaultDatabase.LOGIC_NAME, DefaultDatabase.LOGIC_NAME, "t_encrypt", "pwd", Collections.singletonList(null));
        for (Object each : encryptAssistedQueryValues) {
            assertNull(each);
        }
    }
    
    @Test
    public void assertGetAssistedQueryColumns() {
        assertFalse(new EncryptRule(createEncryptRuleConfiguration()).getAssistedQueryColumns("t_encrypt").isEmpty());
    }
    
    @Test
    public void assertFindPlainColumn() {
        assertTrue(new EncryptRule(createEncryptRuleConfiguration()).findPlainColumn("t_encrypt", "pwd").isPresent());
        assertTrue(new EncryptRule(createEncryptRuleConfiguration()).findPlainColumn("t_encrypt", "credit_card".toLowerCase()).isPresent());
        assertFalse(new EncryptRule(createEncryptRuleConfiguration()).findPlainColumn("t_encrypt", "notExistLogicColumn").isPresent());
    }
    
    @Test
    public void assertFindLikeQueryColumn() {
        assertFalse(new EncryptRule(createEncryptRuleConfiguration()).findLikeQueryColumn("t_encrypt", "pwd_cipher").isPresent());
    }
    
    @Test
    public void assertGetEncryptLikeQueryValues() {
        List<Object> encryptLikeQueryValues = new EncryptRule(createEncryptRuleConfiguration())
                .getEncryptLikeQueryValues(DefaultDatabase.LOGIC_NAME, DefaultDatabase.LOGIC_NAME, "t_encrypt", "pwd", Collections.singletonList(null));
        for (Object actual : encryptLikeQueryValues) {
            assertNull(actual);
        }
    }
    
    @Test
    public void assertGetTables() {
        assertThat(new EncryptRule(createEncryptRuleConfiguration()).getTables(), is(Collections.singleton("t_encrypt")));
    }
    
    @Test
    public void assertGetTableWithLowercase() {
        assertThat(new EncryptRule(createEncryptRuleConfigurationWithUpperCaseLogicTable()).getTables(), is(Collections.singleton("t_encrypt")));
    }
    
    @Test
    public void assertTheSameLogicTable() {
        Collection<String> logicTables = new EncryptRule(createEncryptRuleConfiguration()).getTables();
        Collection<String> theSameLogicTables = new EncryptRule(createEncryptRuleConfigurationWithUpperCaseLogicTable()).getTables();
        assertThat(logicTables, is(theSameLogicTables));
    }
    
    private EncryptRuleConfiguration createEncryptRuleConfiguration() {
        AlgorithmConfiguration queryAssistedEncryptConfig = new AlgorithmConfiguration("CORE.QUERY_ASSISTED.FIXTURE", new Properties());
        AlgorithmConfiguration queryLikeEncryptConfig = new AlgorithmConfiguration("CORE.QUERY_LIKE.FIXTURE", new Properties());
        EncryptColumnRuleConfiguration pwdColumnConfig =
                new EncryptColumnRuleConfiguration("pwd", "pwd_cipher", "pwd_assist", "pwd_like", "pwd_plain", "test_encryptor", "test_encryptor", "like_encryptor", null);
        EncryptColumnRuleConfiguration creditCardColumnConfig = new EncryptColumnRuleConfiguration("credit_card", "credit_card_cipher", "", "", "credit_card_plain", "test_encryptor", null);
        EncryptTableRuleConfiguration tableConfig = new EncryptTableRuleConfiguration("t_encrypt", Arrays.asList(pwdColumnConfig, creditCardColumnConfig), null);
        return new EncryptRuleConfiguration(Collections.singleton(tableConfig), getEncryptors(queryAssistedEncryptConfig, queryLikeEncryptConfig));
    }
    
    @Test
    public void assertAssistedQueryEncryptorNameSpecified() {
        EncryptColumnRuleConfiguration pwdColumnConfig =
                new EncryptColumnRuleConfiguration("pwd", "pwd_cipher", "pwd_assist", "", "pwd_plain", "test_encryptor", "assisted_query_test_encryptor", null, null);
        assertThat(pwdColumnConfig.getAssistedQueryEncryptorName(), is("assisted_query_test_encryptor"));
    }
    
    @Test
    public void assertLikeQueryEncryptorNameSpecified() {
        EncryptColumnRuleConfiguration pwdColumnConfig =
                new EncryptColumnRuleConfiguration("pwd", "pwd_cipher", "", "pwd_like", "pwd_plain", "test_encryptor", "", "like_query_test_encryptor", null);
        assertThat(pwdColumnConfig.getLikeQueryEncryptorName(), is("like_query_test_encryptor"));
    }
    
    private EncryptRuleConfiguration createEncryptRuleConfigurationWithUpperCaseLogicTable() {
        AlgorithmConfiguration queryAssistedEncryptConfig = new AlgorithmConfiguration("CORE.QUERY_ASSISTED.FIXTURE", new Properties());
        AlgorithmConfiguration queryLikeEncryptConfig = new AlgorithmConfiguration("CORE.QUERY_LIKE.FIXTURE", new Properties());
        EncryptColumnRuleConfiguration pwdColumnConfig = new EncryptColumnRuleConfiguration("pwd", "pwd_cipher", "", "", "pwd_plain", "test_encryptor", null);
        EncryptColumnRuleConfiguration creditCardColumnConfig = new EncryptColumnRuleConfiguration("credit_card", "credit_card_cipher", "", "", "credit_card_plain", "test_encryptor", null);
        EncryptTableRuleConfiguration tableConfig = new EncryptTableRuleConfiguration("T_ENCRYPT", Arrays.asList(pwdColumnConfig, creditCardColumnConfig), null);
        return new EncryptRuleConfiguration(Collections.singleton(tableConfig), getEncryptors(queryAssistedEncryptConfig, queryLikeEncryptConfig));
    }
    
    private Map<String, AlgorithmConfiguration> getEncryptors(final AlgorithmConfiguration queryAssistedEncryptConfig, final AlgorithmConfiguration queryLikeEncryptConfig) {
        Map<String, AlgorithmConfiguration> result = new HashMap<>(2, 1);
        result.put("test_encryptor", queryAssistedEncryptConfig);
        result.put("like_encryptor", queryLikeEncryptConfig);
        return result;
    }
}
