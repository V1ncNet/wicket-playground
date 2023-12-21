package de.vinado.app.playground.wicket.configuration;

import de.vinado.app.playground.wicket.navigation.NavigationItemRegistry;

public interface PlaygroundWicketConfigurer extends WicketConfigurer {

    default void addNavigationItems(NavigationItemRegistry registry) {
    }
}
