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

package org.apache.shardingsphere.driver.jdbc.unsupported;

import org.apache.shardingsphere.driver.jdbc.core.resultset.ShardingSphereResultSet;
import org.apache.shardingsphere.infra.executor.sql.context.ExecutionContext;
import org.apache.shardingsphere.infra.merge.result.MergedResult;
import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;

public final class UnsupportedUpdateOperationResultSetTest {
    
    private ShardingSphereResultSet shardingSphereResultSet;
    
    @Before
    public void init() throws SQLException {
        shardingSphereResultSet = new ShardingSphereResultSet(
                Collections.singletonList(mock(ResultSet.class, RETURNS_DEEP_STUBS)), mock(MergedResult.class), mock(Statement.class), mock(ExecutionContext.class));
    }
    
    @Test
    public void assertUpdateNullForColumnIndex() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateNull(1));
    }
    
    @Test
    public void assertUpdateNullForColumnLabel() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateNull("label"));
    }
    
    @Test
    public void assertUpdateBooleanForColumnIndex() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateBoolean(1, false));
    }
    
    @Test
    public void assertUpdateBooleanForColumnLabel() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateBoolean("label", false));
    }
    
    @Test
    public void assertUpdateByteForColumnIndex() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateByte(1, (byte) 1));
    }
    
    @Test
    public void assertUpdateByteForColumnLabel() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateByte("label", (byte) 1));
    }
    
    @Test
    public void assertUpdateShortForColumnIndex() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateShort(1, (short) 1));
    }
    
    @Test
    public void assertUpdateShortForColumnLabel() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateShort("label", (short) 1));
    }
    
    @Test
    public void assertUpdateIntForColumnIndex() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateInt(1, 1));
    }
    
    @Test
    public void assertUpdateIntForColumnLabel() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateInt("label", 1));
    }
    
    @Test
    public void assertUpdateLongForColumnIndex() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateLong(1, 1L));
    }
    
    @Test
    public void assertUpdateLongForColumnLabel() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateLong("label", 1L));
    }
    
    @Test
    public void assertUpdateFloatForColumnIndex() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateFloat(1, 1.0F));
    }
    
    @Test
    public void assertUpdateFloatForColumnLabel() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateFloat("label", 1.0F));
    }
    
    @Test
    public void assertUpdateDoubleForColumnIndex() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateDouble(1, 1.0D));
    }
    
    @Test
    public void assertUpdateDoubleForColumnLabel() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateDouble("label", 1.0D));
    }
    
    @Test
    public void assertUpdateBigDecimalForColumnIndex() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateBigDecimal(1, new BigDecimal("1")));
    }
    
    @Test
    public void assertUpdateBigDecimalForColumnLabel() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateBigDecimal("label", new BigDecimal("1")));
    }
    
    @Test
    public void assertUpdateStringForColumnIndex() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateString(1, "1"));
    }
    
    @Test
    public void assertUpdateStringForColumnLabel() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateString("label", "1"));
    }
    
    @Test
    public void assertUpdateNStringForColumnIndex() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateNString(1, ""));
    }
    
    @Test
    public void assertUpdateNStringForColumnLabel() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateNString("label", ""));
    }
    
    @Test
    public void assertUpdateBytesForColumnIndex() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateBytes(1, new byte[]{}));
    }
    
    @Test
    public void assertUpdateBytesForColumnLabel() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateBytes("label", new byte[]{}));
    }
    
    @Test
    public void assertUpdateDateForColumnIndex() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateDate(1, new Date(0L)));
    }
    
    @Test
    public void assertUpdateDateForColumnLabel() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateDate("label", new Date(0L)));
    }
    
    @Test
    public void assertUpdateTimeForColumnIndex() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateTime(1, new Time(0L)));
    }
    
    @Test
    public void assertUpdateTimeForColumnLabel() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateTime("label", new Time(0L)));
    }
    
    @Test
    public void assertUpdateTimestampForColumnIndex() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateTimestamp(1, new Timestamp(0L)));
    }
    
    @Test
    public void assertUpdateTimestampForColumnLabel() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateTimestamp("label", new Timestamp(0L)));
    }
    
    @Test
    public void assertUpdateAsciiStreamForColumnIndex() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateAsciiStream(1, System.in));
    }
    
    @Test
    public void assertUpdateAsciiStreamForColumnLabel() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateAsciiStream("label", System.in));
    }
    
    @Test
    public void assertUpdateAsciiStreamForColumnIndexWithIntegerLength() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateAsciiStream(1, System.in, 1));
    }
    
    @Test
    public void assertUpdateAsciiStreamForColumnLabelWithIntegerLength() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateAsciiStream("label", System.in, 1));
    }
    
    @Test
    public void assertUpdateAsciiStreamForColumnIndexWithLongLength() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateAsciiStream(1, System.in, 1L));
    }
    
    @Test
    public void assertUpdateAsciiStreamForColumnLabelWithLongLength() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateAsciiStream("label", System.in, 1L));
    }
    
    @Test
    public void assertUpdateBinaryStreamForColumnIndex() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateBinaryStream(1, System.in));
    }
    
    @Test
    public void assertUpdateBinaryStreamForColumnLabel() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateBinaryStream("label", System.in));
    }
    
    @Test
    public void assertUpdateBinaryStreamForColumnIndexWithIntegerLength() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateBinaryStream(1, System.in, 1));
    }
    
    @Test
    public void assertUpdateBinaryStreamForColumnLabelWithIntegerLength() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateBinaryStream("label", System.in, 1));
    }
    
    @Test
    public void assertUpdateBinaryStreamForColumnIndexWithLongLength() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateBinaryStream(1, System.in, 1L));
    }
    
    @Test
    public void assertUpdateBinaryStreamForColumnLabelWithLongLength() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateBinaryStream("label", System.in, 1L));
    }
    
    @Test
    public void assertUpdateCharacterStreamForColumnIndex() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateCharacterStream(1, new StringReader("")));
    }
    
    @Test
    public void assertUpdateCharacterStreamForColumnLabel() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateCharacterStream("label", new StringReader("")));
    }
    
    @Test
    public void assertUpdateCharacterStreamForColumnIndexWithIntegerLength() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateCharacterStream(1, new StringReader(""), 1));
    }
    
    @Test
    public void assertUpdateCharacterStreamForColumnLabelWithIntegerLength() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateCharacterStream("label", new StringReader(""), 1));
    }
    
    @Test
    public void assertUpdateCharacterStreamForColumnIndexWithLongLength() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateCharacterStream(1, new StringReader(""), 1L));
    }
    
    @Test
    public void assertUpdateCharacterStreamForColumnLabelWithLongLength() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateCharacterStream("label", new StringReader(""), 1L));
    }
    
    @Test
    public void assertUpdateNCharacterStreamForColumnIndex() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateNCharacterStream(1, new StringReader("")));
    }
    
    @Test
    public void assertUpdateNCharacterStreamForColumnLabel() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateNCharacterStream("label", new StringReader("")));
    }
    
    @Test
    public void assertUpdateNCharacterStreamForColumnIndexWithLength() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateNCharacterStream(1, new StringReader(""), 1));
    }
    
    @Test
    public void assertUpdateNCharacterStreamForColumnLabelWithLength() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateNCharacterStream("label", new StringReader(""), 1));
    }
    
    @Test
    public void assertUpdateObjectForColumnIndex() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateObject(1, new Object()));
    }
    
    @Test
    public void assertUpdateObjectForColumnLabel() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateObject("label", new Object()));
    }
    
    @Test
    public void assertUpdateObjectForColumnIndexWithScaleOrLength() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateObject(1, new Object(), 1));
    }
    
    @Test
    public void assertUpdateObjectForColumnLabelWithScaleOrLength() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateObject("label", new Object(), 1));
    }
    
    @Test
    public void assertUpdateRefForColumnIndex() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateRef(1, null));
    }
    
    @Test
    public void assertUpdateRefForColumnLabel() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateRef("label", null));
    }
    
    @Test
    public void assertUpdateBlobForColumnIndex() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateBlob(1, (Blob) null));
    }
    
    @Test
    public void assertUpdateBlobForColumnLabel() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateBlob("label", (Blob) null));
    }
    
    @Test
    public void assertUpdateBlobForColumnIndexWithInputStream() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateBlob(1, System.in));
    }
    
    @Test
    public void assertUpdateBlobForColumnLabelWithInputStream() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateBlob("label", System.in));
    }
    
    @Test
    public void assertUpdateBlobForColumnIndexWithInputStreamAndLength() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateBlob(1, System.in, 100));
    }
    
    @Test
    public void assertUpdateBlobForColumnLabelWithInputStreamAndLength() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateBlob("label", System.in, 100));
    }
    
    @Test
    public void assertUpdateClobForColumnIndex() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateClob(1, (Clob) null));
    }
    
    @Test
    public void assertUpdateClobForColumnLabel() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateClob("label", (Clob) null));
    }
    
    @Test
    public void assertUpdateClobForColumnIndexWithInputStream() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateClob(1, new StringReader("")));
    }
    
    @Test
    public void assertUpdateClobForColumnLabelWithInputStream() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateClob("label", new StringReader("")));
    }
    
    @Test
    public void assertUpdateClobForColumnIndexWithInputStreamAndLength() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateClob(1, new StringReader(""), 100));
    }
    
    @Test
    public void assertUpdateClobForColumnLabelWithInputStreamAndLength() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateClob("label", new StringReader(""), 100));
    }
    
    @Test
    public void assertUpdateNClobForColumnIndex() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateNClob(1, (NClob) null));
    }
    
    @Test
    public void assertUpdateNClobForColumnLabel() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateNClob("label", (NClob) null));
    }
    
    @Test
    public void assertUpdateNClobForColumnIndexWithInputStream() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateNClob(1, new StringReader("")));
    }
    
    @Test
    public void assertUpdateNClobForColumnLabelWithInputStream() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateNClob("label", new StringReader("")));
    }
    
    @Test
    public void assertUpdateNClobForColumnIndexWithInputStreamAndLength() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateNClob(1, new StringReader(""), 100));
    }
    
    @Test
    public void assertUpdateNClobForColumnLabelWithInputStreamAndLength() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateNClob("label", new StringReader(""), 100));
    }
    
    @Test
    public void assertUpdateArrayForColumnIndex() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateArray(1, null));
    }
    
    @Test
    public void assertUpdateArrayForColumnLabel() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateArray("label", null));
    }
    
    @Test
    public void assertUpdateRowIdForColumnIndex() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateRowId(1, null));
    }
    
    @Test
    public void assertUpdateRowIdForColumnLabel() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateRowId("label", null));
    }
    
    @Test
    public void assertUpdateSQLXMLForColumnIndex() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateSQLXML(1, null));
    }
    
    @Test
    public void assertUpdateSQXMLForColumnLabel() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereResultSet.updateSQLXML("label", null));
    }
}
