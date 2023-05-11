package de.vinado.app.playground.security.web.oauth2;

import de.vinado.app.playground.security.web.WebSecurityConfigurationSupport;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Profile("oauth2")
@EnableWebSecurity
public class Oauth2WebSecurityConfiguration extends WebSecurityConfigurationSupport {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.oauth2Login()
        ;
    }
}
