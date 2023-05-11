package de.vinado.app.playground.security.web.oauth2;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class Oauth2WebSecurityConfiguration {

    @Bean
    public SecurityFilterChain rootFilterChain(HttpSecurity http) throws Exception {
        http.antMatcher("/**")
            .authorizeHttpRequests(authorize -> authorize
                .anyRequest().permitAll())
            .oauth2Login()
        ;

        return http.build();
    }
}
