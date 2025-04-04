package de.vinado.app.playground.security.web.oauth2;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(fluent = false)
@ConfigurationProperties("app.security.oauth2.client")
public class OidcClientProperties {

    private final Map<String, Registration> registration = new HashMap<>();

    private URI postLogoutRedirectUri;


    @Getter
    @Setter
    @Accessors(fluent = false)
    public static class Registration {

        private String rolesJsonPath;
    }
}
