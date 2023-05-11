package de.vinado.app.playground.testcontainers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

import static org.junit.jupiter.api.Assertions.*;

@JsonTest
public class KeycloakTestcontainerTests extends KeycloakTestCase {

    @Test
    void startingConfiguredKeycloak_shouldProvideAccessToken() {
        String accessToken = retrieveAccessToken();

        assertFalse(accessToken.isEmpty());
    }
}
