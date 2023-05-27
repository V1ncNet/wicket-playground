package de.vinado.app.playground.note.presentation.ui;

import de.vinado.app.playground.wicket.bootstrap.icon.Bi;
import de.vinado.app.playground.wicket.configuration.WicketConfigurer;
import de.vinado.app.playground.wicket.navigation.NavigationItem;
import de.vinado.app.playground.wicket.navigation.NavigationItemRegistry;
import lombok.RequiredArgsConstructor;
import org.apache.wicket.protocol.http.WebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Order(Ordered.HIGHEST_PRECEDENCE + 200)
@Profile("wicket")
@Configuration
@EnableConfigurationProperties(NoteProperties.class)
@RequiredArgsConstructor
public class NoteConfiguration implements WicketConfigurer {

    private final NoteProperties properties;

    @Override
    public void init(WebApplication webApplication) {
        mountPages(webApplication);
    }

    private void mountPages(WebApplication webApplication) {
        webApplication.mountPage("note", NotePage.class);
    }

    @Override
    public void addNavigationItems(NavigationItemRegistry registry) {
        registry.register(NavigationItem.builder(NotePage.class, "Notes").icon(Bi.MARKDOWN).build());
    }
}
