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

package org.apache.shardingsphere.traffic.algorithm.traffic.hint;

import org.apache.shardingsphere.infra.hint.HintValueContext;
import org.apache.shardingsphere.traffic.api.traffic.hint.HintTrafficValue;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class SQLHintTrafficAlgorithmTest {
    
    @Test
    public void assertMatchWhenSQLHintAllMatch() {
        HintValueContext hintValueContext = new HintValueContext();
        hintValueContext.setUseTraffic(Boolean.TRUE);
        assertTrue(new SQLHintTrafficAlgorithm().match(new HintTrafficValue(hintValueContext)));
    }
    
    @Test
    public void assertMatchWhenSQLHintOneMatch() {
        HintValueContext hintValueContext = new HintValueContext();
        hintValueContext.setUseTraffic(Boolean.FALSE);
        assertFalse(new SQLHintTrafficAlgorithm().match(new HintTrafficValue(hintValueContext)));
    }
}
