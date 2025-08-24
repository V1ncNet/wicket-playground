package de.vinado.app.playground.dashboard.presentation.api.v1;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static de.vinado.app.playground.dashboard.presentation.api.v1.DashboardRestController.PATH;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

class DashboardApiV1ConfigurationSupport {

    @Bean
    public SecurityFilterChain dashboardApiV1FilterChain(HttpSecurity http) throws Exception {
        http.securityMatcher(antMatcher(PATH + "/**"))
            .cors(cors -> cors
                .configurationSource(dashboardApiV1CorsConfigurationSource()))
        ;

        configure(http);
        return http.build();
    }

    protected void configure(HttpSecurity http) throws Exception {
    }

    @Bean
    public CorsConfigurationSource dashboardApiV1CorsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("http://localhost:5173");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
