/*
 * Copyright © 2017 Mercateo AG (http://www.mercateo.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mercateo.rest.jersey.utils.cors;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MultivaluedMap;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

public class SimpleAccessControlAllowHeaderFilterIntegrationTest extends JerseyTest {
	@Path("/")
	public static final class TestResource {
		@GET
		public void TestException() {

		}
	}

	@Override
	protected Application configure() {
		ResourceConfig rs = new ResourceConfig(TestResource.class, JacksonFeature.class);
		rs.register(new SimpleAccessControlAllowHeaderFilter());
		return rs;
	}

	@Test
	public void getTest() {

		MultivaluedMap<String, Object> headers = target("/").request().get().getHeaders();

		assertEquals("orgin, content-type, accept, authorization", headers.get("Access-Control-Allow-Headers").get(0));
		assertEquals("true", headers.get("Access-Control-Allow-Credentials").get(0));
		assertEquals("GET, POST, PUT, DELETE, OPTIONS", headers.get("Access-Control-Allow-Methods").get(0));

	}
}
