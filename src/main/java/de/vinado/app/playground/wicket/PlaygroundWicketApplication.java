package de.vinado.app.playground.wicket;

import de.agilecoders.wicket.webjars.WicketWebjars;
import de.agilecoders.wicket.webjars.settings.WebjarsSettings;
import de.vinado.app.playground.document.presentation.ui.PreviewPage;
import de.vinado.app.playground.wicket.bootstrap.BootstrapResourceAppender;
import de.vinado.app.playground.wicket.configuration.PlaygroundWicketConfigurer;
import org.apache.wicket.Page;
import org.apache.wicket.application.ComponentInitializationListenerCollection;
import org.apache.wicket.csp.CSPDirective;
import org.apache.wicket.csp.CSPDirectiveSrcValue;
import org.apache.wicket.csp.ContentSecurityPolicySettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Profile("wicket")
@Component
public class PlaygroundWicketApplication extends WicketApplication {

    private final List<PlaygroundWicketConfigurer> configurers;

    public PlaygroundWicketApplication(@Autowired(required = false) List<PlaygroundWicketConfigurer> configurers) {
        this.configurers = configurers;
    }

    @Override
    public Class<? extends Page> getHomePage() {
        return PreviewPage.class;
    }

    @Override
    protected void init() {
        super.init();

        WicketWebjars.install(this, getWebjarsSettings());

        configurers().forEach(configurer -> configurer.init(this));
    }

    @Override
    protected void configure(ContentSecurityPolicySettings settings) {
        super.configure(settings);

        settings.blocking().strict()
            .add(CSPDirective.STYLE_SRC, CSPDirectiveSrcValue.SELF)
            .add(CSPDirective.IMG_SRC, "data:")
        ;
    }

    @Override
    protected void configure(ComponentInitializationListenerCollection listeners) {
        super.configure(listeners);

        listeners.add(new BootstrapResourceAppender());
    }

    @Override
    protected void configure(WebjarsSettings settings) {
        super.configure(settings);

        settings.useCdnResources(false);
    }

    private Stream<PlaygroundWicketConfigurer> configurers() {
        return Optional.ofNullable(configurers).stream()
            .flatMap(List::stream);
    }
}