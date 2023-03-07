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

package org.apache.shardingsphere.test.it.sql.parser.internal.cases.parser.jaxb.segment.impl.definition;

import lombok.Getter;
import lombok.Setter;
<<<<<<<< HEAD:test/it/parser/src/main/java/org/apache/shardingsphere/test/it/sql/parser/internal/cases/parser/jaxb/segment/impl/definition/ExpectedIndexDefinition.java
import org.apache.shardingsphere.test.it.sql.parser.internal.cases.parser.jaxb.segment.AbstractExpectedSQLSegment;
import org.apache.shardingsphere.test.it.sql.parser.internal.cases.parser.jaxb.segment.impl.index.ExpectedIndex;
========
import org.apache.shardingsphere.distsql.parser.segment.AlgorithmSegment;
import org.apache.shardingsphere.test.it.sql.parser.internal.cases.parser.jaxb.SQLParserTestCase;
>>>>>>>> apache-master:test/it/parser/src/main/java/org/apache/shardingsphere/test/it/sql/parser/internal/cases/parser/jaxb/statement/ral/LockClusterStatementTestCase.java

import javax.xml.bind.annotation.XmlElement;

/**
<<<<<<<< HEAD:test/it/parser/src/main/java/org/apache/shardingsphere/test/it/sql/parser/internal/cases/parser/jaxb/segment/impl/definition/ExpectedIndexDefinition.java
 * Expected index definition.
 */
@Getter
@Setter
public final class ExpectedIndexDefinition extends AbstractExpectedSQLSegment {
    
    @XmlElement
    private ExpectedIndex index;
========
 * Lock cluster statement test case.
 */
@Getter
@Setter
public final class LockClusterStatementTestCase extends SQLParserTestCase {
    
    @XmlElement(name = "lock-strategy")
    private AlgorithmSegment lockStrategy;
>>>>>>>> apache-master:test/it/parser/src/main/java/org/apache/shardingsphere/test/it/sql/parser/internal/cases/parser/jaxb/statement/ral/LockClusterStatementTestCase.java
}
