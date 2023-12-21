package de.vinado.app.playground.wicket.playground.configuration;

import de.vinado.app.playground.wicket.playground.navigation.ConfigurableNavigationItemSupplier;
import de.vinado.app.playground.wicket.playground.navigation.NavigationItemRegistry;
import de.vinado.app.playground.wicket.playground.navigation.NavigationItemSupplier;
import org.springframework.context.annotation.Bean;

public class PlaygroundWicketConfigurationSupport {

    @Bean
    public NavigationItemSupplier navigationItemSupplier() {
        ConfigurableNavigationItemSupplier supplier = new ConfigurableNavigationItemSupplier();
        addNavigationItems(supplier);
        return supplier;
    }

    protected void addNavigationItems(NavigationItemRegistry registry) {
    }
}
