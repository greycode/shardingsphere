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

package org.apache.shardingsphere.infra.merge.result.impl.local;

import org.junit.Test;

import java.sql.SQLFeatureNotSupportedException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class LocalDataMergedResultTest {
    
    @Test
    public void assertNext() {
        LocalDataQueryResultRow row = new LocalDataQueryResultRow("value");
        LocalDataMergedResult actual = new LocalDataMergedResult(Collections.singletonList(row));
        assertTrue(actual.next());
        assertFalse(actual.next());
    }
    
    @Test
    public void assertGetValue() {
        LocalDataQueryResultRow row = new LocalDataQueryResultRow("value");
        LocalDataMergedResult actual = new LocalDataMergedResult(Collections.singletonList(row));
        assertTrue(actual.next());
        assertThat(actual.getValue(1, Object.class).toString(), is("value"));
    }
    
    @Test
    public void assertGetCalendarValue() {
        LocalDataQueryResultRow row = new LocalDataQueryResultRow(new Date(0L));
        LocalDataMergedResult actual = new LocalDataMergedResult(Collections.singletonList(row));
        assertTrue(actual.next());
        assertThat(actual.getCalendarValue(1, Object.class, Calendar.getInstance()), is(new Date(0L)));
    }
    
    @Test
    public void assertGetInputStream() {
        List<Object> row = Collections.singletonList("value");
        LocalDataMergedResult actual = new LocalDataMergedResult(Collections.singletonList(new LocalDataQueryResultRow(row)));
        assertThrows(SQLFeatureNotSupportedException.class, () -> actual.getInputStream(1, "Ascii"));
    }
    
    @Test
    public void assertWasNull() {
        List<Object> row = Collections.singletonList("value");
        LocalDataMergedResult actual = new LocalDataMergedResult(Collections.singletonList(new LocalDataQueryResultRow(row)));
        assertTrue(actual.next());
        assertFalse(actual.wasNull());
    }
}
