package de.vinado.app.playground.note.presentation.ui;

import de.vinado.app.playground.wicket.bootstrap.icon.Bi;
import de.vinado.app.playground.wicket.codimd.CodiMdUrlProvider;
import de.vinado.app.playground.wicket.configuration.WicketConfigurer;
import de.vinado.app.playground.wicket.navigation.NavigationItem;
import de.vinado.app.playground.wicket.navigation.NavigationItemRegistry;
import lombok.RequiredArgsConstructor;
import org.apache.wicket.csp.CSPDirective;
import org.apache.wicket.csp.ContentSecurityPolicySettings;
import org.apache.wicket.protocol.http.WebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URL;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Order(Ordered.HIGHEST_PRECEDENCE + 200)
@Profile("wicket")
@Configuration
@EnableConfigurationProperties(NoteProperties.class)
@RequiredArgsConstructor
public class NoteConfiguration implements WicketConfigurer {

    private static final String PATH = "note";

    private final NoteProperties properties;

    @Override
    public void init(WebApplication webApplication) {
        ContentSecurityPolicySettings cspSettings = webApplication.getCspSettings();
        configure(cspSettings);

        mountPages(webApplication);
    }

    private void configure(ContentSecurityPolicySettings settings) {
        settings.blocking()
            .add(CSPDirective.FRAME_SRC, getCodiMdBaseUrl() + "/");
    }

    private void mountPages(WebApplication webApplication) {
        webApplication.mountPage(PATH, NotePage.class);
    }

    @Override
    public void addNavigationItems(NavigationItemRegistry registry) {
        registry.register(NavigationItem.builder(NotePage.class, "Notes").icon(Bi.MARKDOWN).build());
    }

    @Bean
    public CodiMdUrlProvider codiMdUrlProvider() {
        return note -> UriComponentsBuilder.fromHttpUrl(getCodiMdBaseUrl().toString())
            .path("{noteId}")
            .build(note.getId())
            .toURL();
    }

    private URL getCodiMdBaseUrl() {
        NoteProperties.Codimd codimd = properties.getCodimd();
        return codimd.getBaseUrl();
    }


    @Profile("oauth2")
    @Configuration
    static class WebConfiguration {

        @Bean
        public SecurityFilterChain noteFilterChain(HttpSecurity http) throws Exception {
            http.securityMatcher(antMatcher("/" + PATH + "/**"))
                .authorizeHttpRequests(authorize -> authorize
                    .anyRequest().authenticated())
                .oauth2Login()
            ;

            return http.build();
        }
    }
}
