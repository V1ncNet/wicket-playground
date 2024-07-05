package de.vinado.app.playground.testcontainers;

import com.fasterxml.jackson.annotation.JsonProperty;
import dasniko.testcontainers.keycloak.KeycloakContainer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import lombok.Data;

@Testcontainers
public class KeycloakTestCase {

    static final String REALM_NAME = "playground";
    static final String CLIENT_ID = "wicket";
    static final String CLIENT_SECRET = "etgWLsvbyKRdxFXIyeZpWbor99sKjQ42";
    static final String GRANT_TYPE = "password";
    static final String SCOPE = "openid";

    @Container
    static final KeycloakContainer keycloak = new KeycloakContainer("quay.io/keycloak/keycloak:22.0")
        .withAdminUsername("landlord")
        .withAdminPassword("Prop3r7y")
        .withRealmImportFile("/" + REALM_NAME + "-realm.json");

    protected static String retrieveAccessToken() throws RestClientException {
        RestTemplate client = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> entity = prepareTokenRetrieval();
        ResponseEntity<TokenResponse> response = client.exchange(
            keycloak.getAuthServerUrl() + "/realms/" + REALM_NAME + "/protocol/openid-connect/token",
            HttpMethod.POST,
            entity,
            TokenResponse.class);

        return response.getBody().accessToken();
    }

    private static HttpEntity<MultiValueMap<String, String>> prepareTokenRetrieval() {
        HttpHeaders headers = prepareHeaders();
        MultiValueMap<String, String> properties = prepareBody();
        return new HttpEntity<>(properties, headers);
    }

    private static HttpHeaders prepareHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        return headers;
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


    @Data
    private static final class TokenResponse {

        @JsonProperty("access_token")
        private String accessToken;
    }
}
