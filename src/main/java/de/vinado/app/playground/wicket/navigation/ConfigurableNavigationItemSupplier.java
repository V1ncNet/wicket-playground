package de.vinado.app.playground.wicket.navigation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ConfigurableNavigationItemSupplier implements NavigationItemRegistry, NavigationItemSupplier {

    private final List<NavigationItem> store = new ArrayList<>();

    @Override
    public void register(NavigationItem navigationItem) {
        store.add(navigationItem);
    }

    @Override
    public Stream<NavigationItem> get() {
        return store.stream();
    }
}
