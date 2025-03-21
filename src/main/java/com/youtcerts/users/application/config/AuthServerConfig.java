package com.youtcerts.users.application.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "youtcerts.auth-server")
@Getter
@Setter
public class AuthServerConfig {

	private String basePath;
	private String authenticationPath;
	private String usersCreationPath;
	private String clientId;
	private String clientSecret;

	public String getUsersCreationEndpoint() {
		return basePath + usersCreationPath;
	}

	public String getAuthenticationEndpoint() {
		return basePath + authenticationPath;
	}

}