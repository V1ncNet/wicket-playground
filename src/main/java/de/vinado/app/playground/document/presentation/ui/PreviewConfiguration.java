package de.vinado.app.playground.document.presentation.ui;

import de.vinado.app.playground.wicket.configuration.WicketConfigurer;
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
    }

    private void configure(ContentSecurityPolicySettings settings) {
        settings.blocking()
            .add(CSPDirective.FRAME_SRC, properties.getBaseUrl() + "/");
    }

    @Bean
    public PreviewUrlProvider previewUrlProvider() {
        return document -> UriComponentsBuilder.fromHttpUrl(properties.getBaseUrl().toString())
            .path("/preview/pdf")
            .query("url={url}")
            .build(document.getUri())
            .toURL();
    }
}
