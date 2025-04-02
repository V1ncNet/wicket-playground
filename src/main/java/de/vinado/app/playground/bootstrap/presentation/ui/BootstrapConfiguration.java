package de.vinado.app.playground.bootstrap.presentation.ui;

import de.vinado.app.playground.wicket.bootstrap.icon.Bi;
import de.vinado.app.playground.wicket.configuration.WicketConfigurer;
import de.vinado.app.playground.wicket.navigation.NavigationItem;
import de.vinado.app.playground.wicket.navigation.NavigationItemRegistry;
import org.apache.wicket.protocol.http.WebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import lombok.RequiredArgsConstructor;

@Order(Ordered.HIGHEST_PRECEDENCE + 50)
@Profile("wicket")
@Configuration
@RequiredArgsConstructor
public class BootstrapConfiguration implements WicketConfigurer {

    @Override
    public void init(WebApplication webApplication) {
        mountPages(webApplication);
    }

    private void mountPages(WebApplication webApplication) {
        webApplication.mountPage("bootstrap", BootstrapPage.class);
    }

    @Override
    public void addNavigationItems(NavigationItemRegistry registry) {
        registry.register(NavigationItem.builder(BootstrapPage.class, "Bootstrap").icon(Bi.BOOTSTRAP).build());
    }
}
