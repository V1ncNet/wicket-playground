package de.vinado.app.playground.wicket.configuration;

import org.apache.wicket.protocol.http.WebApplication;

public interface WicketConfigurer {

    default void init(WebApplication webApplication) {
    }
}
