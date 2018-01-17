package org.roof.oauth2.service.api;

import org.springframework.security.oauth2.common.OAuth2AccessToken;

public interface ITokenService {
	public OAuth2AccessToken getAccessToken(String username);
}
