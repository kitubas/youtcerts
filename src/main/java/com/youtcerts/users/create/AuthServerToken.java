package com.youtcerts.users.create;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthServerToken {

	@JsonProperty("access_token")
	private String accessToken;
	@JsonProperty("expires_in")
	private long expiresIn;
	private LocalDateTime receivedAt;

	public boolean isExpired() {
		var receivedAtIsNull = receivedAt == null;
		var accessTokenIsNull = accessToken == null;
		var expiresInIsZero = expiresIn == 0;
		if (receivedAtIsNull || accessTokenIsNull || expiresInIsZero) {
			return true;
		}
		var expirationTime = receivedAt.plusSeconds(expiresIn);
		return LocalDateTime.now().isAfter(expirationTime);
	}
}
