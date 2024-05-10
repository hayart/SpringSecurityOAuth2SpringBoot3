package am.developer.outh.security;


import am.developer.outh.model.OauthClientDetail;
import am.developer.outh.repository.OauthClientDetailsRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.time.Instant;
import java.util.Set;

@Service
public class JpaRegisteredClientRepository implements RegisteredClientRepository {

	@Value("${user.oauth.accessTokenValidity}")
	private long tokenExpirationSeconds;

	private final OauthClientDetailsRepository clientRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	public JpaRegisteredClientRepository(OauthClientDetailsRepository clientRepository,
										 BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        Assert.notNull(clientRepository, "clientRepository cannot be null");
		this.clientRepository = clientRepository;
	}

	@Override
	public void save(RegisteredClient registeredClient) {
		Assert.notNull(registeredClient, "registeredClient cannot be null");
		this.clientRepository.save(toEntity(registeredClient));
	}

	@Override
	public RegisteredClient findById(String id) {
		Assert.hasText(id, "id cannot be empty");
		return this.clientRepository.findById(id).map(this::toObject).orElse(null);
	}

	@Override
	public RegisteredClient findByClientId(String clientId) {
		Assert.hasText(clientId, "clientId cannot be empty");
		return this.clientRepository.findById(clientId).map(this::toObject).orElse(null);
	}

	private RegisteredClient toObject(OauthClientDetail client) {
		Set<String> authorizationGrantTypes = StringUtils.commaDelimitedListToSet(
				AuthorizationGrantType.CLIENT_CREDENTIALS.getValue());
		Set<String> clientScopes = Set.of("all");
		Instant now = Instant.now();
		RegisteredClient.Builder builder = RegisteredClient.withId(client.getId())
				.clientId(client.getId())
				.clientSecret(this.passwordEncoder.encode(client.getSecret()))
				.clientName(client.getName())
				.clientSecretExpiresAt(now.plus(Duration.ofSeconds(tokenExpirationSeconds)))
				.authorizationGrantTypes(grantTypes ->
						authorizationGrantTypes.forEach(grantType ->
								grantTypes.add(resolveAuthorizationGrantType(grantType))))
				.scopes(scopes -> scopes.addAll(clientScopes));

		return builder.build();
	}

	private OauthClientDetail toEntity(RegisteredClient registeredClient) {
		OauthClientDetail entity = new OauthClientDetail();
		entity.setId(registeredClient.getId());
		entity.setSecret(registeredClient.getClientSecret());
		entity.setName(registeredClient.getClientName());

		return entity;
	}

	private static AuthorizationGrantType resolveAuthorizationGrantType(String authorizationGrantType) {
		if (AuthorizationGrantType.AUTHORIZATION_CODE.getValue().equals(authorizationGrantType)) {
			return AuthorizationGrantType.AUTHORIZATION_CODE;
		} else if (AuthorizationGrantType.CLIENT_CREDENTIALS.getValue().equals(authorizationGrantType)) {
			return AuthorizationGrantType.CLIENT_CREDENTIALS;
		} else if (AuthorizationGrantType.REFRESH_TOKEN.getValue().equals(authorizationGrantType)) {
			return AuthorizationGrantType.REFRESH_TOKEN;
		}
		return new AuthorizationGrantType(authorizationGrantType);              // Custom authorization grant type
	}
}
