package de.vinado.app.playground.security.web.oauth2;

import de.vinado.app.playground.security.web.WebSecurityConfigurationSupport;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

import lombok.RequiredArgsConstructor;

@Profile("oauth2")
@EnableWebSecurity
@Configuration
@EnableConfigurationProperties(OidcClientProperties.class)
@RequiredArgsConstructor
public class Oauth2WebSecurityConfiguration extends WebSecurityConfigurationSupport {

    private final ClientRegistrationRepository clientRegistrationRepository;
    private final OidcClientProperties properties;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        OidcClientInitiatedLogoutSuccessHandler frontChannelLogoutHandler = new OidcClientInitiatedLogoutSuccessHandler(clientRegistrationRepository);
        frontChannelLogoutHandler.setPostLogoutRedirectUri(properties.getPostLogoutRedirectUri().toString());

        http
            .oauth2Login(Customizer.withDefaults())
            .logout(logout -> logout
                .logoutSuccessHandler(frontChannelLogoutHandler)
                .invalidateHttpSession(true)
                .permitAll())
        ;
    }
}
