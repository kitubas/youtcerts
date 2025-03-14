package com.youtcerts.users.create;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class CreateUserUseCase extends RouteBuilder {

	private static AuthServerToken token;


	@Override
	public void configure() throws Exception {
		from("direct:createUser")
			.log("Creating user")

			.log("User created");
	}


	private static class AuthServerToken {

		private String accessToken;
		private String expiresIn;

	}
}
