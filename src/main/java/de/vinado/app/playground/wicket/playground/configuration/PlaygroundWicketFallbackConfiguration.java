package de.vinado.app.playground.wicket.playground.configuration;

import de.vinado.app.playground.wicket.playground.navigation.NavigationItemSupplier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.stream.Stream;

@Profile("!playground")
@Configuration
public class PlaygroundWicketFallbackConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public NavigationItemSupplier navigationItemSupplier() {
        return Stream::empty;
    }
}
