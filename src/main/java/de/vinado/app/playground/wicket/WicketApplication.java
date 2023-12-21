package de.vinado.app.playground.wicket;

import de.agilecoders.wicket.webjars.settings.WebjarsSettings;
import lombok.Setter;
import org.apache.wicket.application.ComponentInitializationListenerCollection;
import org.apache.wicket.application.ComponentInstantiationListenerCollection;
import org.apache.wicket.csp.ContentSecurityPolicySettings;
import org.apache.wicket.markup.html.HeaderResponseDecoratorCollection;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.resource.caching.NoOpResourceCachingStrategy;
import org.apache.wicket.settings.DebugSettings;
import org.apache.wicket.settings.MarkupSettings;
import org.apache.wicket.settings.ResourceSettings;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.nio.charset.StandardCharsets;

public abstract class WicketApplication extends WebApplication implements ApplicationContextAware {

    @Setter
    private ApplicationContext applicationContext;

    @Setter
    private WebjarsSettings webjarsSettings;

    @Override
    protected void init() {
        super.init();

        MarkupSettings markupSettings = getMarkupSettings();
        configure(markupSettings);

        ContentSecurityPolicySettings cspSettings = getCspSettings();
        configure(cspSettings);

        ComponentInstantiationListenerCollection componentInstantiationListeners = getComponentInstantiationListeners();
        configure(componentInstantiationListeners);

        ComponentInitializationListenerCollection componentInitializationListeners = getComponentInitializationListeners();
        configure(componentInitializationListeners);

        WebjarsSettings webjarsSettings = getWebjarsSettings();
        configure(webjarsSettings);

        ResourceSettings resourceSettings = getResourceSettings();
        configure(resourceSettings);

        DebugSettings debugSettings = getDebugSettings();
        configure(debugSettings);

        HeaderResponseDecoratorCollection headerResponseDecorators = getHeaderResponseDecorators();
        configure(headerResponseDecorators);
    }

    protected void configure(MarkupSettings settings) {
        settings.setDefaultMarkupEncoding(StandardCharsets.UTF_8.name());
        settings.setCompressWhitespace(usesDeploymentConfig());
        settings.setStripComments(usesDeploymentConfig());
        settings.setStripWicketTags(true);
    }

    protected void configure(ContentSecurityPolicySettings settings) {
        settings.blocking().strict();
    }

    protected void configure(ComponentInstantiationListenerCollection listeners) {
        listeners.add(new SpringComponentInjector(this, applicationContext));
    }

    protected void configure(ComponentInitializationListenerCollection listeners) {
    }

    protected void configure(WebjarsSettings settings) {
    }

    protected void configure(ResourceSettings settings) {
        settings.setResourcePollFrequency(null);
        settings.setCssCompressor(new YuiCssCompressor());
        settings.setJavaScriptCompressor(new GoogleClosureJavaScriptCompressor());
        settings.setCachingStrategy(new NoOpResourceCachingStrategy());
    }

    protected void configure(DebugSettings settings) {
        if (usesDevelopmentConfig()) {
            settings.setComponentPathAttributeName("data-wicket-path");
            settings.setOutputMarkupContainerClassName(true);
        }
    }

    protected void configure(HeaderResponseDecoratorCollection decorators) {
        decorators.add(new JavaScriptFilteredIntoFooterHeaderResponseDecorator());
    }

    public WebjarsSettings getWebjarsSettings() {
        if (null == webjarsSettings) {
            webjarsSettings = new WebjarsSettings();
        }
        return webjarsSettings;
    }
}
