/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.usergrid.persistence.graph.impl;


import java.util.UUID;

import org.apache.usergrid.persistence.core.util.ValidationUtils;
import org.apache.usergrid.persistence.graph.Edge;
import org.apache.usergrid.persistence.graph.SearchByEdgeType;
import org.apache.usergrid.persistence.graph.serialization.util.EdgeUtils;
import org.apache.usergrid.persistence.model.entity.Id;

import com.google.common.base.Optional;


/**
 *
 *
 */
public class SimpleSearchByEdgeType implements SearchByEdgeType{

    private final Id node;
    private final String type;
    private final long maxTimestamp;
    private final Optional<Edge> last;


    /**
     * Create the search modules
     * @param node The node to search from
     * @param type The edge type
     * @param maxTimestamp The maximum timestamp to return
     * @param last The value to start seeking from.  Must be >= this value
     */
    public SimpleSearchByEdgeType( final Id node, final String type, final long maxTimestamp, final Edge last ) {
        ValidationUtils.verifyIdentity(node);
        ValidationUtils.verifyString( type, "type" );
        EdgeUtils.validateTimestamp(maxTimestamp, "maxTimestamp");


        this.node = node;
        this.type = type;
        this.maxTimestamp = maxTimestamp;
        this.last = Optional.fromNullable(last);
    }


    @Override
    public Id getNode() {
        return node;
    }


    @Override
    public String getType() {
        return type;
    }


    @Override
    public long getMaxTimestamp() {
        return maxTimestamp;
    }


    @Override
    public Optional<Edge> last() {
        return last;
    }


    @Override
    public boolean equals( final Object o ) {
        if ( this == o ) {
            return true;
        }
        if ( !( o instanceof SimpleSearchByEdgeType ) ) {
            return false;
        }

        final SimpleSearchByEdgeType that = ( SimpleSearchByEdgeType ) o;

        if ( maxTimestamp != that.maxTimestamp ) {
            return false;
        }
        if ( !last.equals( that.last ) ) {
            return false;
        }
        if ( !node.equals( that.node ) ) {
            return false;
        }
        if ( !type.equals( that.type ) ) {
            return false;
        }

        return true;
    }


    @Override
    public int hashCode() {
        int result = node.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + ( int ) ( maxTimestamp ^ ( maxTimestamp >>> 32 ) );
        result = 31 * result + last.hashCode();
        return result;
    }
}