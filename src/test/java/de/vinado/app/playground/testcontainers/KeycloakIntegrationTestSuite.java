package de.vinado.app.playground.testcontainers;

import com.fasterxml.jackson.annotation.JsonProperty;
import dasniko.testcontainers.keycloak.KeycloakContainer;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.function.Supplier;

import lombok.Data;

@Testcontainers
public class KeycloakIntegrationTestSuite {

    static final String REALM_NAME = "test";
    static final String CLIENT_ID = "wicket";
    static final String CLIENT_SECRET = "etgWLsvbyKRdxFXIyeZpWbor99sKjQ42";
    static final String GRANT_TYPE = "password";
    static final String SCOPE = "openid";

    @Container
    static final KeycloakContainer keycloak = new KeycloakContainer()
        .withAdminUsername("landlord")
        .withAdminPassword("Prop3r7y")
        .withRealmImportFile("/" + REALM_NAME + "-realm.json");

    protected static String retrieveAccessToken() throws RestClientException {
        MultiValueMap<String, String> body = prepareBody();
        RestClient.ResponseSpec response = RestClient.create()
            .post()
            .uri(keycloak.getAuthServerUrl() + "/realms/{realmName}/protocol/openid-connect/token", REALM_NAME)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .accept(MediaType.APPLICATION_JSON)
            .body(body)
            .retrieve();

        TokenResponse tokenResponse = response.body(TokenResponse.class);
        return tokenResponse.accessToken();
    }

    private static MultiValueMap<String, String> prepareBody() {
        MultiValueMap<String, String> properties = new LinkedMultiValueMap<>();
        properties.add("grant_type", GRANT_TYPE);
        properties.add("username", "adult");
        properties.add("password", "B4nk");
        properties.add("client_id", CLIENT_ID);
        properties.add("client_secret", CLIENT_SECRET);
        properties.add("scope", SCOPE);
        return properties;
    }

    @DynamicPropertySource
    static void keycloakProperties(DynamicPropertyRegistry registry) {
        Supplier<Object> issuerUri = resolveIssuerUri();
        registry.add("spring.security.oauth2.resourceserver.jwt.issuer-uri", issuerUri);
    }

    static Supplier<Object> resolveIssuerUri() {
        return () -> UriComponentsBuilder.newInstance()
            .scheme("http")
            .host(keycloak.getHost())
            .port(keycloak.getHttpPort())
            .pathSegment("realms", REALM_NAME)
            .toUriString();
    }


    @Data
    private static final class TokenResponse {

        @JsonProperty("access_token")
        private String accessToken;
    }
}
