package com.youtcerts.users.create;

import com.youtcerts.users.application.config.AuthServerConfig;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateUserUseCase extends RouteBuilder {

	private static AuthServerToken token = new AuthServerToken();
	private final AuthServerConfig authServerConfig;


	@Override
	public void configure() throws Exception {
		var dontThrowExceptionOnFailure = "?throwExceptionOnFailure=false";
		var bridgeEndpoint = "&bridgeEndpoint=true";

		from("direct:createUser")
				.log("Creating user: ${body}")
				.process(CreateUserUseCase::inputAccessToken)
				.log("Token successfully retrieved ${header.Authorization}")
				.removeHeader(Exchange.HTTP_URI)
				.setHeader(Exchange.HTTP_METHOD, simple("${header.CamelHttpMethod}"))
				.marshal().json()
				.to(authServerConfig.getUsersCreationEndpoint() + dontThrowExceptionOnFailure + bridgeEndpoint)
				.unmarshal().json()
				.log("User ${body} created");
	}

	private static void inputAccessToken(Exchange exchange) {
		if (token == null || token.isExpired()) {
			token = exchange.getContext().createProducerTemplate()
					.requestBody("direct:generateClientCredentialsToken", null, AuthServerToken.class);

			token.setReceivedAt(LocalDateTime.now());
			exchange.getIn().setHeader("Authorization", "Bearer " + token.getAccessToken());
		}
	}
}
