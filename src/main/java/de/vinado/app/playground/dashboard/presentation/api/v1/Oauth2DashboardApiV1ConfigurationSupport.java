package de.vinado.app.playground.dashboard.presentation.api.v1;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Profile("oauth2")
@Configuration
class Oauth2DashboardApiV1ConfigurationSupport extends DashboardApiV1ConfigurationSupport {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .oauth2ResourceServer(server -> server
                .jwt(Customizer.withDefaults()))
            .authorizeHttpRequests(authorize -> authorize
                .anyRequest().authenticated())
        ;
    }
}
