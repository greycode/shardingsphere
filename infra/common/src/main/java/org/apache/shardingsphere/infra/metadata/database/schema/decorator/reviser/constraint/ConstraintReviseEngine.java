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

package org.apache.shardingsphere.infra.metadata.database.schema.decorator.reviser.constraint;

import org.apache.shardingsphere.infra.metadata.database.schema.loader.model.ConstraintMetaData;
import org.apache.shardingsphere.infra.rule.ShardingSphereRule;
import org.apache.shardingsphere.infra.util.spi.type.typed.TypedSPILoader;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Optional;

/**
 * Constraint revise engine.
 * 
 * @param <T> type of rule
 */
public final class ConstraintReviseEngine<T extends ShardingSphereRule> {
    
    /**
     * Revise constraint meta data.
     * 
     * @param tableName table name
     * @param originalMetaDataList original constraint meta data list
     * @param rule rule
     * @return revised constraint meta data
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public Collection<ConstraintMetaData> revise(final String tableName, final Collection<ConstraintMetaData> originalMetaDataList, final T rule) {
        Optional<ConstraintReviser> reviser = TypedSPILoader.findService(ConstraintReviser.class, rule.getClass().getSimpleName());
        if (!reviser.isPresent()) {
            return originalMetaDataList;
        }
        Collection<ConstraintMetaData> result = new LinkedHashSet<>();
        for (ConstraintMetaData each : originalMetaDataList) {
            Optional<ConstraintMetaData> constraintMetaData = reviser.get().revise(tableName, each, rule);
            constraintMetaData.ifPresent(result::add);
        }
        return result;
    }
}
