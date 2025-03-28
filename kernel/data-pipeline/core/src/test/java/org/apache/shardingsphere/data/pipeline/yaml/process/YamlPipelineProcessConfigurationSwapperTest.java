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

package org.apache.shardingsphere.data.pipeline.yaml.process;

import org.apache.shardingsphere.data.pipeline.api.config.process.PipelineProcessConfiguration;
import org.apache.shardingsphere.data.pipeline.api.config.process.PipelineReadConfiguration;
import org.apache.shardingsphere.data.pipeline.api.config.process.PipelineWriteConfiguration;
import org.apache.shardingsphere.infra.config.algorithm.AlgorithmConfiguration;
import org.apache.shardingsphere.infra.yaml.config.pojo.algorithm.YamlAlgorithmConfiguration;
import org.apache.shardingsphere.test.util.PropertiesBuilder;
import org.apache.shardingsphere.test.util.PropertiesBuilder.Property;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

public final class YamlPipelineProcessConfigurationSwapperTest {
    
    @Test
    public void assertSwapToObject() {
        PipelineProcessConfiguration actual = new YamlPipelineProcessConfigurationSwapper().swapToObject(createYamlConfiguration());
        assertThat(actual.getRead().getWorkerThread(), is(40));
        assertThat(actual.getRead().getBatchSize(), is(1000));
        assertThat(actual.getRead().getShardingSize(), is(10000000));
        assertThat(actual.getRead().getRateLimiter().getType(), is("INPUT"));
        assertThat(actual.getRead().getRateLimiter().getProps().getProperty("batch-size"), is("1000"));
        assertThat(actual.getRead().getRateLimiter().getProps().getProperty("qps"), is("50"));
        assertThat(actual.getWrite().getWorkerThread(), is(40));
        assertThat(actual.getWrite().getBatchSize(), is(1000));
        assertThat(actual.getWrite().getRateLimiter().getType(), is("OUTPUT"));
        assertThat(actual.getWrite().getRateLimiter().getProps().getProperty("batch-size"), is("1000"));
        assertThat(actual.getWrite().getRateLimiter().getProps().getProperty("qps"), is("50"));
        assertThat(actual.getStreamChannel().getType(), is("MEMORY"));
        assertThat(actual.getStreamChannel().getProps().getProperty("block-queue-size"), is("10000"));
    }
    
    private YamlPipelineProcessConfiguration createYamlConfiguration() {
        YamlPipelineReadConfiguration yamlInputConfig = YamlPipelineReadConfiguration.buildWithDefaultValue();
        yamlInputConfig.setRateLimiter(new YamlAlgorithmConfiguration("INPUT", PropertiesBuilder.build(new Property("batch-size", "1000"), new Property("qps", "50"))));
        YamlPipelineProcessConfiguration result = new YamlPipelineProcessConfiguration();
        result.setRead(yamlInputConfig);
        YamlPipelineWriteConfiguration yamlOutputConfig = YamlPipelineWriteConfiguration.buildWithDefaultValue();
        yamlOutputConfig.setRateLimiter(new YamlAlgorithmConfiguration("OUTPUT", PropertiesBuilder.build(new Property("batch-size", "1000"), new Property("qps", "50"))));
        result.setWrite(yamlOutputConfig);
        result.setStreamChannel(new YamlAlgorithmConfiguration("MEMORY", PropertiesBuilder.build(new Property("block-queue-size", "10000"))));
        return result;
    }
    
    @Test
    public void assertSwapToYamlConfiguration() {
        PipelineReadConfiguration readConfig = new PipelineReadConfiguration(40, 1000, 10000000,
                new AlgorithmConfiguration("INPUT", PropertiesBuilder.build(new Property("batch-size", "1000"), new Property("qps", "50"))));
        PipelineWriteConfiguration writeConfig = new PipelineWriteConfiguration(40, 1000,
                new AlgorithmConfiguration("OUTPUT", PropertiesBuilder.build(new Property("batch-size", "1000"), new Property("qps", "50"))));
        PipelineProcessConfiguration config = new PipelineProcessConfiguration(readConfig, writeConfig,
                new AlgorithmConfiguration("MEMORY", PropertiesBuilder.build(new Property("block-queue-size", "10000"))));
        YamlPipelineProcessConfiguration actual = new YamlPipelineProcessConfigurationSwapper().swapToYamlConfiguration(config);
        assertThat(actual.getRead().getWorkerThread(), is(40));
        assertThat(actual.getRead().getBatchSize(), is(1000));
        assertThat(actual.getRead().getShardingSize(), is(10000000));
        assertThat(actual.getRead().getRateLimiter().getType(), is("INPUT"));
        assertThat(actual.getRead().getRateLimiter().getProps().getProperty("batch-size"), is("1000"));
        assertThat(actual.getRead().getRateLimiter().getProps().getProperty("qps"), is("50"));
        assertThat(actual.getWrite().getWorkerThread(), is(40));
        assertThat(actual.getWrite().getBatchSize(), is(1000));
        assertThat(actual.getWrite().getRateLimiter().getType(), is("OUTPUT"));
        assertThat(actual.getWrite().getRateLimiter().getProps().getProperty("batch-size"), is("1000"));
        assertThat(actual.getWrite().getRateLimiter().getProps().getProperty("qps"), is("50"));
        assertThat(actual.getStreamChannel().getType(), is("MEMORY"));
        assertThat(actual.getStreamChannel().getProps().getProperty("block-queue-size"), is("10000"));
    }
    
    @Test
    public void assertSwapToYamlConfigurationWithNull() {
        assertNull(new YamlPipelineProcessConfigurationSwapper().swapToYamlConfiguration(null));
    }
    
    @Test
    public void assertSwapToObjectWithNull() {
        assertNull(new YamlPipelineProcessConfigurationSwapper().swapToObject(null));
    }
}
