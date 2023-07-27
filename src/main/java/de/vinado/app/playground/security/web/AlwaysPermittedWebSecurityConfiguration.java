package de.vinado.app.playground.security.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Profile("!oauth2")
@EnableWebSecurity
@Configuration
public class AlwaysPermittedWebSecurityConfiguration extends WebSecurityConfigurationSupport {
}
