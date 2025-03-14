package com.youtcerts.users.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UsersRoutes extends RouteBuilder {

	private final int port;

	public UsersRoutes(@Value("${server.port}") int port) {
		this.port = port;
	}

	@Override
	public void configure() throws Exception {

		restConfiguration()
				.component("netty-http")
				.host("localhost")
				.bindingMode(RestBindingMode.auto)
				.port(port);

		rest("/v1/users")
			.post().to("direct:createUser")
			.post("/auth").to("direct:authenticateUser");
	}
}
