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

package org.apache.shardingsphere.db.protocol.postgresql.packet.command;

import org.apache.shardingsphere.db.protocol.postgresql.exception.PostgreSQLProtocolException;
import org.apache.shardingsphere.db.protocol.postgresql.payload.PostgreSQLPacketPayload;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class PostgreSQLCommandPacketTypeLoaderTest {
    
    @Test
    public void assertGetCommandPacketType() {
        PostgreSQLPacketPayload payload = mock(PostgreSQLPacketPayload.class, RETURNS_DEEP_STUBS);
        when(payload.getByteBuf().getByte(anyInt())).thenReturn((byte) 'Q');
        assertThat(PostgreSQLCommandPacketTypeLoader.getCommandPacketType(payload), is(PostgreSQLCommandPacketType.SIMPLE_QUERY));
    }
    
    @Test
    public void assertGetCommandPacketTypeError() {
        PostgreSQLPacketPayload payload = mock(PostgreSQLPacketPayload.class, RETURNS_DEEP_STUBS);
        when(payload.getByteBuf().getByte(anyInt())).thenReturn((byte) 'a');
        assertThrows(PostgreSQLProtocolException.class, () -> PostgreSQLCommandPacketTypeLoader.getCommandPacketType(payload));
    }
}
