package com.youtcerts.users.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class AuthServerRoutes extends RouteBuilder {
	@Override
	public void configure() throws Exception {
		rest("/v1/users")
			.post().to("direct:createUser")
			.post("/auth").to("direct:authenticateUser");
	}
}
