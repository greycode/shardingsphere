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

package org.apache.shardingsphere.shadow.route.engine.dml;

import org.apache.shardingsphere.infra.binder.statement.dml.SelectStatementContext;
import org.apache.shardingsphere.infra.config.algorithm.AlgorithmConfiguration;
import org.apache.shardingsphere.infra.route.context.RouteContext;
import org.apache.shardingsphere.infra.route.context.RouteMapper;
import org.apache.shardingsphere.infra.route.context.RouteUnit;
import org.apache.shardingsphere.shadow.api.config.ShadowRuleConfiguration;
import org.apache.shardingsphere.shadow.api.config.datasource.ShadowDataSourceConfiguration;
import org.apache.shardingsphere.shadow.api.config.table.ShadowTableConfiguration;
import org.apache.shardingsphere.shadow.rule.ShadowRule;
import org.apache.shardingsphere.sql.parser.sql.common.segment.dml.column.ColumnSegment;
import org.apache.shardingsphere.sql.parser.sql.common.segment.dml.expr.BinaryOperationExpression;
import org.apache.shardingsphere.sql.parser.sql.common.segment.dml.expr.simple.LiteralExpressionSegment;
import org.apache.shardingsphere.sql.parser.sql.common.segment.dml.predicate.WhereSegment;
import org.apache.shardingsphere.sql.parser.sql.common.segment.generic.CommentSegment;
import org.apache.shardingsphere.sql.parser.sql.common.segment.generic.table.SimpleTableSegment;
import org.apache.shardingsphere.sql.parser.sql.common.segment.generic.table.TableNameSegment;
import org.apache.shardingsphere.sql.parser.sql.common.value.identifier.IdentifierValue;
import org.apache.shardingsphere.sql.parser.sql.dialect.statement.mysql.dml.MySQLSelectStatement;
import org.apache.shardingsphere.test.util.PropertiesBuilder;
import org.apache.shardingsphere.test.util.PropertiesBuilder.Property;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class ShadowSelectStatementRoutingEngineTest {
    
    private ShadowSelectStatementRoutingEngine shadowRouteEngine;
    
    @Before
    public void init() {
        shadowRouteEngine = new ShadowSelectStatementRoutingEngine(createSelectStatementContext(), Collections.emptyList());
    }
    
    private SelectStatementContext createSelectStatementContext() {
        SelectStatementContext result = mock(SelectStatementContext.class);
        when(result.getAllTables()).thenReturn(Collections.singleton(new SimpleTableSegment(new TableNameSegment(20, 25, new IdentifierValue("t_order")))));
        BinaryOperationExpression binaryOperationExpression = mock(BinaryOperationExpression.class);
        when(binaryOperationExpression.getLeft()).thenReturn(new ColumnSegment(0, 0, new IdentifierValue("user_id")));
        when(binaryOperationExpression.getRight()).thenReturn(new LiteralExpressionSegment(0, 0, "1"));
        when(result.getWhereSegments()).thenReturn(Collections.singleton(new WhereSegment(0, 0, binaryOperationExpression)));
        MySQLSelectStatement selectStatement = new MySQLSelectStatement();
        selectStatement.getCommentSegments().add(new CommentSegment("/*shadow:true,foo:bar*/", 0, 20));
        selectStatement.getCommentSegments().add(new CommentSegment("/*aaa:bbb*/", 21, 30));
        when(result.getSqlStatement()).thenReturn(selectStatement);
        return result;
    }
    
    @Test
    public void assertRouteAndParseShadowColumnConditions() {
        RouteContext routeContext = mock(RouteContext.class);
        when(routeContext.getRouteUnits()).thenReturn(Collections.singleton(new RouteUnit(new RouteMapper("ds", "ds_shadow"), Collections.emptyList())));
        shadowRouteEngine.route(routeContext, new ShadowRule(createShadowRuleConfiguration()));
        Optional<Collection<String>> sqlNotes = shadowRouteEngine.parseSQLComments();
        assertTrue(sqlNotes.isPresent());
        assertThat(sqlNotes.get().size(), is(2));
        Iterator<String> sqlNotesIt = sqlNotes.get().iterator();
        assertThat(sqlNotesIt.next(), is("/*shadow:true,foo:bar*/"));
        assertThat(sqlNotesIt.next(), is("/*aaa:bbb*/"));
    }
    
    private ShadowRuleConfiguration createShadowRuleConfiguration() {
        ShadowRuleConfiguration result = new ShadowRuleConfiguration();
        result.setDataSources(Collections.singletonList(new ShadowDataSourceConfiguration("shadow-data-source-0", "ds", "ds_shadow")));
        result.setTables(Collections.singletonMap("t_order", new ShadowTableConfiguration(Collections.singleton("shadow-data-source-0"), Collections.singleton("user-id-select-regex-algorithm"))));
        result.setShadowAlgorithms(Collections.singletonMap("user-id-select-regex-algorithm", createShadowAlgorithm()));
        return result;
    }
    
    private AlgorithmConfiguration createShadowAlgorithm() {
        return new AlgorithmConfiguration("REGEX_MATCH", PropertiesBuilder.build(new Property("column", "user_id"), new Property("operation", "select"), new Property("regex", "[1]")));
    }
    
    @Test
    public void assertGetAllTables() {
        Collection<SimpleTableSegment> actual = shadowRouteEngine.getAllTables();
        assertThat(actual.size(), is(1));
        assertThat(actual.iterator().next().getTableName().getIdentifier().getValue(), is("t_order"));
    }
}
