package de.vinado.app.playground.dashboard.presentation.api.v1;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Profile("!oauth2")
@Configuration
class UnsecuredDashboardApiV1Configuration extends DashboardApiV1ConfigurationSupport {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .oauth2ResourceServer(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(authorize -> authorize
                .anyRequest().permitAll())
        ;
    }
}
