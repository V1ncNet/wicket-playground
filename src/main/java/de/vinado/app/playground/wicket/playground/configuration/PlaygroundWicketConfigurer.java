package de.vinado.app.playground.wicket.playground.configuration;

import de.vinado.app.playground.wicket.configuration.WicketConfigurer;
import de.vinado.app.playground.wicket.playground.navigation.NavigationItemRegistry;

public interface PlaygroundWicketConfigurer extends WicketConfigurer {

    default void addNavigationItems(NavigationItemRegistry registry) {
    }
}
