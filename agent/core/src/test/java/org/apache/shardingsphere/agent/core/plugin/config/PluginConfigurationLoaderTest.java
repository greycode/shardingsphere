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

package org.apache.shardingsphere.agent.core.plugin.config;

import org.apache.shardingsphere.agent.api.PluginConfiguration;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;
import java.util.Objects;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNull;

public final class PluginConfigurationLoaderTest {
    
    @Test
    public void assertLoad() throws IOException {
        Map<String, PluginConfiguration> actual = PluginConfigurationLoader.load(new File(getResourceURL()));
        assertThat(actual.size(), is(3));
        assertLoggingPluginConfiguration(actual.get("LogFixture"));
        assertMetricsPluginConfiguration(actual.get("MetricsFixture"));
        assertTracingPluginConfiguration(actual.get("TracingFixture"));
    }
    
    private String getResourceURL() throws UnsupportedEncodingException {
        return URLDecoder.decode(Objects.requireNonNull(PluginConfigurationLoader.class.getClassLoader().getResource("")).getFile(), "UTF8");
    }
    
    private void assertLoggingPluginConfiguration(final PluginConfiguration actual) {
        assertNull(actual.getHost());
        assertThat(actual.getPort(), is(0));
        assertNull(actual.getPassword());
        assertThat(actual.getProps().size(), is(1));
        assertThat(actual.getProps().getProperty("logging_key"), is("logging_value"));
    }
    
    private void assertMetricsPluginConfiguration(final PluginConfiguration actual) {
        assertThat(actual.getHost(), is("metrics.host"));
        assertThat(actual.getPort(), is(1));
        assertThat(actual.getPassword(), is("metrics.pwd"));
        assertThat(actual.getProps().size(), is(1));
        assertThat(actual.getProps().getProperty("metrics_key"), is("metrics_value"));
    }
    
    private void assertTracingPluginConfiguration(final PluginConfiguration actual) {
        assertThat(actual.getHost(), is("tracing.host"));
        assertThat(actual.getPort(), is(2));
        assertThat(actual.getPassword(), is("tracing.pwd"));
        assertThat(actual.getProps().size(), is(1));
        assertThat(actual.getProps().getProperty("tracing_key"), is("tracing_value"));
    }
}
