package org.roof.oauth2.service.api;

import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

public interface ITokenServices
		extends AuthorizationServerTokenServices, ResourceServerTokenServices, ConsumerTokenServices {

}
