package de.vinado.app.playground.wicket.configuration;

import de.vinado.app.playground.wicket.navigation.ConfigurableNavigationItemSupplier;
import de.vinado.app.playground.wicket.navigation.NavigationItemRegistry;
import de.vinado.app.playground.wicket.navigation.NavigationItemSupplier;
import org.springframework.context.annotation.Bean;

public class WicketConfigurationSupport {

    @Bean
    public NavigationItemSupplier navigationItemSupplier() {
        ConfigurableNavigationItemSupplier supplier = new ConfigurableNavigationItemSupplier();
        addNavigationItems(supplier);
        return supplier;
    }

    protected void addNavigationItems(NavigationItemRegistry registry) {
    }
}
