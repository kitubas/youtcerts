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

	private String url;
	private String authenticationPath;
	private String clientId;
	private String clientSecret;

}