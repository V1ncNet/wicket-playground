package de.vinado.app.playground.wicket;

import de.agilecoders.wicket.webjars.WicketWebjars;
import de.agilecoders.wicket.webjars.settings.WebjarsSettings;
import de.vinado.app.playground.wicket.bootstrap.BootstrapResourceAppender;
import lombok.Setter;
import org.apache.wicket.Page;
import org.apache.wicket.application.ComponentInitializationListenerCollection;
import org.apache.wicket.application.ComponentInstantiationListenerCollection;
import org.apache.wicket.csp.ContentSecurityPolicySettings;
import org.apache.wicket.markup.html.HeaderResponseDecoratorCollection;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.ws.WebSocketAwareResourceIsolationRequestCycleListener;
import org.apache.wicket.request.cycle.RequestCycleListenerCollection;
import org.apache.wicket.request.resource.caching.NoOpResourceCachingStrategy;
import org.apache.wicket.settings.DebugSettings;
import org.apache.wicket.settings.MarkupSettings;
import org.apache.wicket.settings.ResourceSettings;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Profile("wicket")
@Component
public class WicketApplication extends WebApplication implements ApplicationContextAware {

    @Setter
    private ApplicationContext applicationContext;

    @Setter
    private WebjarsSettings webjarsSettings;

    @Override
    public Class<? extends Page> getHomePage() {
        return HomePage.class;
    }

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

        RequestCycleListenerCollection requestCycleListeners = getRequestCycleListeners();
        configure(requestCycleListeners);

        mountPages();

        WicketWebjars.install(this, webjarsSettings);
    }

    private void configure(MarkupSettings settings) {
        settings.setDefaultMarkupEncoding(StandardCharsets.UTF_8.name());
        settings.setCompressWhitespace(usesDeploymentConfig());
        settings.setStripComments(usesDeploymentConfig());
    }

    private void configure(ContentSecurityPolicySettings settings) {
        settings.blocking().strict()
        ;
    }

    private void configure(ComponentInstantiationListenerCollection listeners) {
        listeners.add(new SpringComponentInjector(this, applicationContext));
    }

    private void configure(ComponentInitializationListenerCollection listeners) {
        listeners.add(new BootstrapResourceAppender());
    }

    private void configure(WebjarsSettings settings) {
        settings.useCdnResources(false);
    }

    private void configure(ResourceSettings settings) {
        settings.setResourcePollFrequency(null);
        settings.setCssCompressor(new YuiCssCompressor());
        settings.setJavaScriptCompressor(new GoogleClosureJavaScriptCompressor());
        settings.setCachingStrategy(new NoOpResourceCachingStrategy());
    }

    private void configure(DebugSettings settings) {
        if (usesDeploymentConfig()) {
            settings.setComponentUseCheck(false);
        } else {
            settings.setComponentPathAttributeName("data-wicket-path");
            settings.setOutputMarkupContainerClassName(usesDevelopmentConfig());
        }
    }

    private void configure(HeaderResponseDecoratorCollection decorators) {
        decorators.add(new JavaScriptFilteredIntoFooterHeaderResponseDecorator());
    }

    private void configure(RequestCycleListenerCollection listeners) {
        listeners.add(new WebSocketAwareResourceIsolationRequestCycleListener());
    }

    private void mountPages() {
    }

    public WebjarsSettings getWebjarsSettings() {
        if (null == webjarsSettings) {
            webjarsSettings = new WebjarsSettings();
        }
        return webjarsSettings;
    }
}
