package de.vinado.app.playground.testcontainers;

import de.vinado.app.playground.test.LongRunningTests;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

import static org.junit.jupiter.api.Assertions.assertFalse;

@LongRunningTests
@JsonTest
public class KeycloakTestcontainerTests extends KeycloakIntegrationTestSuite {

    @Test
    void startingConfiguredKeycloak_shouldProvideAccessToken() {
        String accessToken = retrieveAccessToken();

        assertFalse(accessToken.isEmpty());
    }
}
