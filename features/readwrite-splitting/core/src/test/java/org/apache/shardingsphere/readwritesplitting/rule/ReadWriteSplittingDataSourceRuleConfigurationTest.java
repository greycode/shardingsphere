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

package org.apache.shardingsphere.readwritesplitting.rule;

import org.apache.shardingsphere.readwritesplitting.api.rule.ReadwriteSplittingDataSourceRuleConfiguration;
import org.apache.shardingsphere.readwritesplitting.api.strategy.DynamicReadwriteSplittingStrategyConfiguration;
import org.apache.shardingsphere.readwritesplitting.api.strategy.StaticReadwriteSplittingStrategyConfiguration;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;

public final class ReadWriteSplittingDataSourceRuleConfigurationTest {
    
    private ReadwriteSplittingDataSourceRuleConfiguration readwriteSplittingDataSourceRuleConfig;
    
    private ReadwriteSplittingDataSourceRuleConfiguration readwriteSplittingDataSourceRuleConfigDynamic;
    
    @Before
    public void setup() {
        readwriteSplittingDataSourceRuleConfig = new ReadwriteSplittingDataSourceRuleConfiguration("ds",
                new StaticReadwriteSplittingStrategyConfiguration("write_ds", Arrays.asList("read_ds_0", "read_ds_1")), null, "");
        readwriteSplittingDataSourceRuleConfigDynamic = new ReadwriteSplittingDataSourceRuleConfiguration("ds", null,
                new DynamicReadwriteSplittingStrategyConfiguration("readwrite_ds"), "");
    }
    
    @Test
    public void assertStaticReadWriteSplittingConfig() {
        assertNotNull(readwriteSplittingDataSourceRuleConfig.getStaticStrategy());
        StaticReadwriteSplittingStrategyConfiguration actual = readwriteSplittingDataSourceRuleConfig.getStaticStrategy();
        assertThat(actual.getWriteDataSourceName(), is("write_ds"));
        assertThat(actual.getReadDataSourceNames(), is(Arrays.asList("read_ds_0", "read_ds_1")));
    }
    
    @Test
    public void assertDynamicReadWriteSplittingConfig() {
        assertNotNull(readwriteSplittingDataSourceRuleConfigDynamic.getDynamicStrategy());
        DynamicReadwriteSplittingStrategyConfiguration actual = readwriteSplittingDataSourceRuleConfigDynamic.getDynamicStrategy();
        assertThat(actual.getAutoAwareDataSourceName(), is("readwrite_ds"));
    }
}
