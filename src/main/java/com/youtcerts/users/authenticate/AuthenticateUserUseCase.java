package com.youtcerts.users.authenticate;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AuthenticateUserUseCase extends RouteBuilder {

	private final String clientId;
	private final String clientSecret;
	private final String authServerUrl;

	public static final String CLIENT_ID = "client_id";
	public static final String CLIENT_SECRET = "client_secret";
	public static final String GRANT_TYPE = "grant_type";

	public AuthenticateUserUseCase(@Value("youtcerts.auth-server.client-id") String clientId,
			@Value("youtcerts.auth-server.client-secret") String clientSecret, @Value("youtcerts.auth-server.url") String authServerUrl) {
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.authServerUrl = authServerUrl;
	}

	@Override
	public void configure() throws Exception {
		from("direct:authenticateUser")
				.log("Authenticating user with username: ${header.username}")  // Log the username
				.setBody(simple("client_id=" + clientId + "&client_secret=" + clientSecret +
						"&grant_type=password&username=${header.username}&password=${header.password}"))
				.setHeader(Exchange.CONTENT_TYPE, constant("application/x-www-form-urlencoded"))
				.to(authServerUrl)
				.log("User: ${header.username} - authenticated successfully");

		from("direct:generateClientCredentialsToken")
				.log("Generating client credentials token")
				.setBody(simple("client_id=" + clientId + "&client_secret=" + clientSecret + "&grant_type=client_credentials"))
				.setHeader(Exchange.CONTENT_TYPE, constant("application/x-www-form-urlencoded"))
				.to(authServerUrl)
				.log("Client credentials token generated successfully");
	}
}
