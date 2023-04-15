package de.vinado.app.playground.wicket.bootstrap;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.application.IComponentInitializationListener;

import java.util.Optional;

public class BootstrapResourceAppender implements IComponentInitializationListener {

    @Override
    public void onInitialize(Component component) {
        Optional.of(component)
            .filter(Page.class::isInstance)
            .ifPresent(page -> page.add(BootstrapBehavior.getInstance()));
    }
}
