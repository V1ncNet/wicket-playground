package de.vinado.app.playground.wicket.configuration;

import de.vinado.app.playground.wicket.navigation.NavigationItemRegistry;
import org.apache.wicket.protocol.http.WebApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Profile("wicket")
@Configuration
public class DelegatingWicketConfiguration extends WicketConfigurationSupport {

    private final WicketConfigurerComposite configurers = new WicketConfigurerComposite();

    @Autowired(required = false)
    public void setConfigurers(List<WicketConfigurer> configurers) {
        if (!CollectionUtils.isEmpty(configurers)) {
            this.configurers.addWicketConfigurers(configurers);
        }
    }

    @Override
    protected void addNavigationItems(NavigationItemRegistry registry) {
        this.configurers.addNavigationItems(registry);
    }


    private static class WicketConfigurerComposite implements WicketConfigurer {

        private final List<WicketConfigurer> delegates = new ArrayList<>();

        public void addWicketConfigurers(List<WicketConfigurer> configurers) {
            delegates.addAll(configurers);
        }

        @Override
        public void init(WebApplication webApplication) {
            delegates.forEach(configurer -> configurer.init(webApplication));
        }

        @Override
        public void addNavigationItems(NavigationItemRegistry registry) {
            delegates.forEach(configurer -> configurer.addNavigationItems(registry));
        }
    }
}
