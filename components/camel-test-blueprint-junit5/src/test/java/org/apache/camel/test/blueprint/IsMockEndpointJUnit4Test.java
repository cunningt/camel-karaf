/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.test.blueprint;

import org.apache.camel.EndpointInject;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IsMockEndpointJUnit4Test extends CamelBlueprintTestSupport {
   
    @EndpointInject (value = "mock:seda:result")
    private MockEndpoint mockSeda;

    @EndpointInject (value = "mock:bar")
    private MockEndpoint mockBar;

    @EndpointInject (value = "mock:baz")
    private MockEndpoint mockBaz;
    
    @Override
    protected String getBlueprintDescriptor() {
        return "org/apache/camel/test/blueprint/IsMockEndpoints.xml";
    }

    @Override
    public String isMockEndpoints() {
        return "*";
    }


    @Test
    public void testMockAllEndpoints() throws Exception {
        mockSeda.expectedBodiesReceived("bar");
        mockBar.expectedBodiesReceived("bar");

        template.sendBody("direct:foo", "Hello World");

        assertNotNull(context.hasEndpoint("mock:seda:result"));
        assertNotNull(context.hasEndpoint("mock:baz"));
        assertMockEndpointsSatisfied();
    }

    @Test
    public void testMockBar() throws Exception {
        mockBar.expectedBodiesReceived("bar");

        template.sendBody("direct:foo", "Hello World");

        assertNotNull(context.hasEndpoint("mock:bar"));
        assertMockEndpointsSatisfied();
    }

    @Test
    public void testMockBaz() throws Exception {
        mockBaz.expectedBodiesReceived("baz");

        template.sendBody("direct:foo", "Hello World");

        assertNotNull(context.hasEndpoint("mock:baz"));
        assertMockEndpointsSatisfied();
    }
}