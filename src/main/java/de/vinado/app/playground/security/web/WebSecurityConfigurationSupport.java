package de.vinado.app.playground.security.web;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

public class WebSecurityConfigurationSupport {

    @Bean
    public SecurityFilterChain rootFilterChain(HttpSecurity http) throws Exception {
        http.antMatcher("/**")
            .authorizeHttpRequests(authorize -> authorize
                .anyRequest().permitAll())
        ;

        configure(http);
        return http.build();
    }

    protected void configure(HttpSecurity http) throws Exception {
    }
}
