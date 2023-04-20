package de.vinado.app.playground.wicket.configuration;

import de.vinado.app.playground.wicket.navigation.NavigationItemRegistry;
import org.apache.wicket.protocol.http.WebApplication;

public interface WicketConfigurer {

    default void init(WebApplication webApplication) {
    }

    default void addNavigationItems(NavigationItemRegistry registry) {
    }
}
