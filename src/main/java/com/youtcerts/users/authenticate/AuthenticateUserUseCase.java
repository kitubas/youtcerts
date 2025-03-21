package com.youtcerts.users.authenticate;

import com.youtcerts.users.application.config.AuthServerConfig;
import com.youtcerts.users.create.AuthServerToken;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticateUserUseCase extends RouteBuilder {

	private final AuthServerConfig authServerConfig;

	@Override
	public void configure() throws Exception {
		var dontThrowExceptionOnFailure = "?throwExceptionOnFailure=false";

		from("direct:authenticateUser")
				.setBody(simple("client_id=" + authServerConfig.getClientId()
						+ "&client_secret=" + authServerConfig.getClientSecret()
						+ "&grant_type=password&username=${header.username}&password=${header.password}"))
				.setHeader(Exchange.CONTENT_TYPE, constant("application/x-www-form-urlencoded"))
				.setHeader(Exchange.HTTP_URI, constant(authServerConfig.getAuthenticationEndpoint()))
				.log("Authenticating user with username: ${header.username}")
				.to(authServerConfig.getAuthenticationEndpoint() + dontThrowExceptionOnFailure)
				.log("User: ${header.username} - authenticated successfully");

		from("direct:generateClientCredentialsToken")
				.log("Generating client credentials token")
				.setBody(simple("client_id=" + authServerConfig.getClientId()
						+ "&client_secret=" + authServerConfig.getClientSecret()
						+ "&grant_type=client_credentials"))
				.setHeader(Exchange.CONTENT_TYPE, constant("application/x-www-form-urlencoded"))
				.setHeader(Exchange.HTTP_URI, constant(authServerConfig.getAuthenticationEndpoint()))
				.to(authServerConfig.getAuthenticationEndpoint() + dontThrowExceptionOnFailure)
				.unmarshal().json(JsonLibrary.Jackson, AuthServerToken.class)
				.log("Client credentials token generated successfully");
	}
}
