package de.vinado.app.playground.inbound.presentation.ui;

import de.vinado.app.playground.bootstrap.presentation.ui.BootstrapComponentsPage;
import de.vinado.app.playground.wicket.bootstrap.icon.Bi;
import de.vinado.app.playground.wicket.configuration.WicketConfigurer;
import de.vinado.app.playground.wicket.navigation.NavigationItem;
import de.vinado.app.playground.wicket.navigation.NavigationItemRegistry;
import lombok.RequiredArgsConstructor;
import org.apache.wicket.protocol.http.WebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Profile("wicket")
@Configuration
@RequiredArgsConstructor
class ImportConfiguration implements WicketConfigurer {

    @Override
    public void init(WebApplication webApplication) {
        mountPages(webApplication);
    }

    private void mountPages(WebApplication webApplication) {
        webApplication.mountPage("import", ImportPage.class);
    }

    @Override
    public void addNavigationItems(NavigationItemRegistry registry) {
        registry.register(NavigationItem.builder(ImportPage.class, "Import")
            .icon(Bi.BOX_ARROW_IN_DOWN)
            .build());
    }
}
