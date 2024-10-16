package de.vinado.app.playground.server;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
class WebSecurityConfiguration {

    @Bean
    public SecurityFilterChain oauth2ResourceServerSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
            .securityMatcher("/api/**")
            .authorizeHttpRequests(requests -> requests
                .anyRequest().permitAll())
            .oauth2ResourceServer(server -> server
                .jwt(Customizer.withDefaults()))
            .build();
    }

    @Bean
    public SecurityFilterChain actuatorSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
            .securityMatcher(EndpointRequest.toAnyEndpoint())
            .authorizeHttpRequests(requests -> requests
                .requestMatchers("/actuator", "/actuator/health").permitAll()
                .anyRequest().authenticated())
            .build();
    }
}
