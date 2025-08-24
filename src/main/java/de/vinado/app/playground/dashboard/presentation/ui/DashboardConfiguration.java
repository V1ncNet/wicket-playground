package de.vinado.app.playground.dashboard.presentation.ui;

import de.vinado.app.playground.wicket.bootstrap.icon.Bi;
import de.vinado.app.playground.wicket.configuration.WicketConfigurer;
import de.vinado.app.playground.wicket.navigation.NavigationItem;
import de.vinado.app.playground.wicket.navigation.NavigationItemRegistry;
import org.apache.wicket.protocol.http.WebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Order(Ordered.HIGHEST_PRECEDENCE + 2)
@Profile("wicket")
@Configuration
@RequiredArgsConstructor
public class DashboardConfiguration implements WicketConfigurer {

    @Override
    public void init(WebApplication webApplication) {
        mountPages(webApplication);
    }

    private void mountPages(WebApplication webApplication) {
        webApplication.mountPage(DashboardPage.PATH, DashboardPage.class);
    }

    @Override
    public void addNavigationItems(NavigationItemRegistry registry) {
        registry.register(NavigationItem.builder(DashboardPage.class, "React Dashboard")
            .icon(Bi.SPEEDOMETER)
            .build());
    }


    @Profile("oauth2")
    @Configuration
    static class WebConfiguration {

        @Bean
        public SecurityFilterChain dashboardFilterChain(HttpSecurity http) throws Exception {
            http.securityMatcher(antMatcher("/" + DashboardPage.PATH + "/**"))
                .authorizeHttpRequests(authorize -> authorize
                    .anyRequest().authenticated())
                .oauth2Login(Customizer.withDefaults())
            ;

            return http.build();
        }
    }
}
