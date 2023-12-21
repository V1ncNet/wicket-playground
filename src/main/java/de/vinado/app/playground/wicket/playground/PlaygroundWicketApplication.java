package de.vinado.app.playground.wicket.playground;

import de.agilecoders.wicket.webjars.WicketWebjars;
import de.agilecoders.wicket.webjars.settings.WebjarsSettings;
import de.vinado.app.playground.document.presentation.ui.PreviewPage;
import de.vinado.app.playground.wicket.WicketApplication;
import de.vinado.app.playground.wicket.configuration.WicketConfigurer;
import de.vinado.app.playground.wicket.playground.bootstrap.BootstrapResourceAppender;
import org.apache.wicket.Page;
import org.apache.wicket.application.ComponentInitializationListenerCollection;
import org.apache.wicket.csp.CSPDirective;
import org.apache.wicket.csp.CSPDirectiveSrcValue;
import org.apache.wicket.csp.ContentSecurityPolicySettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Profile("wicket & playground")
@Component
public class PlaygroundWicketApplication extends WicketApplication {

    public PlaygroundWicketApplication(@Autowired(required = false) List<? extends WicketConfigurer> configurers) {
        super(configurers);
    }

    @Override
    public Class<? extends Page> getHomePage() {
        return PreviewPage.class;
    }

    @Override
    protected void init() {
        super.init();

        WicketWebjars.install(this, getWebjarsSettings());
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
}
