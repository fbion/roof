package org.roof.oauth2.service;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.security.oauth2.common.util.SerializationUtils;

/**
 *
 * @author liuxin
 *
 */
public class TokenService implements org.roof.oauth2.service.api.ITokenService {

	private static final Logger LOG = Logger.getLogger(TokenService.class);

	private static final String CLIENT_ID = "client_id";

	private static final String SCOPE = "scope";

	private static final String USERNAME = "username";

	private static final String DEFAULT_ACCESS_TOKEN_FROM_AUTHENTICATION_SELECT_STATEMENT = "select token_id, token from oauth_access_token where authentication_id = ?";

	private String selectAccessTokenFromAuthenticationSql = DEFAULT_ACCESS_TOKEN_FROM_AUTHENTICATION_SELECT_STATEMENT;

	private JdbcTemplate jdbcTemplate;

	public OAuth2AccessToken getAccessToken(String username) {
		Map<String, String> values = new LinkedHashMap<String, String>();
		values.put(USERNAME, username);
		values.put(CLIENT_ID, username);
		values.put(SCOPE, OAuth2Utils.formatParameterList(CollectionUtils.EMPTY_COLLECTION));
		String key = null;
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("MD5 algorithm not available.  Fatal (should be in the JDK).");
		}

		try {
			byte[] bytes = digest.digest(values.toString().getBytes("UTF-8"));
			key = String.format("%032x", new BigInteger(1, bytes));
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("UTF-8 encoding not available.  Fatal (should be in the JDK).");
		}
		OAuth2AccessToken accessToken = null;
		try {
			accessToken = jdbcTemplate.queryForObject(selectAccessTokenFromAuthenticationSql,
					new RowMapper<OAuth2AccessToken>() {
						public OAuth2AccessToken mapRow(ResultSet rs, int rowNum) throws SQLException {
							return deserializeAccessToken(rs.getBytes(2));
						}
					}, key);
		} catch (EmptyResultDataAccessException e) {
			if (LOG.isInfoEnabled()) {
				LOG.debug("Failed to find access token for authentication " + username);
			}
		} catch (IllegalArgumentException e) {
			LOG.error("Could not extract access token for authentication " + username);
		}

		return accessToken;
	}

	protected OAuth2AccessToken deserializeAccessToken(byte[] token) {
		return SerializationUtils.deserialize(token);
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}
