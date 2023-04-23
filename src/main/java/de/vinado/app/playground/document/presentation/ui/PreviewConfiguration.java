package de.vinado.app.playground.document.presentation.ui;

import de.vinado.app.playground.wicket.bootstrap.icon.Bi;
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
import org.springframework.web.util.UriComponentsBuilder;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Profile("wicket")
@Configuration
@EnableConfigurationProperties(PreviewProperties.class)
@RequiredArgsConstructor
public class PreviewConfiguration implements WicketConfigurer {

    private final PreviewProperties properties;

    @Override
    public void init(WebApplication webApplication) {
        ContentSecurityPolicySettings cspSettings = webApplication.getCspSettings();
        configure(cspSettings);

        mountPages(webApplication);
    }

    private void configure(ContentSecurityPolicySettings settings) {
        settings.blocking()
            .add(CSPDirective.FRAME_SRC, properties.getBaseUrl() + "/");
    }

    private void mountPages(WebApplication webApplication) {
        webApplication.mountPage("preview", PreviewPage.class);
    }

    @Override
    public void addNavigationItems(NavigationItemRegistry registry) {
        registry.register(NavigationItem.builder(PreviewPage.class, "Document Preview").icon(Bi.FILETYPE_PDF).build());
    }

    @Bean
    public PreviewUrlProvider previewUrlProvider() {
        return document -> UriComponentsBuilder.fromHttpUrl(properties.getBaseUrl().toString())
            .path("/preview/pdf")
            .query("uri={uri}")
            .build(document.getUri())
            .toURL();
    }
}
