package de.vinado.app.playground.wicket;

import de.agilecoders.wicket.webjars.WicketWebjars;
import de.agilecoders.wicket.webjars.settings.WebjarsSettings;
import de.vinado.app.playground.document.presentation.ui.PreviewPage;
import de.vinado.app.playground.wicket.bootstrap.BootstrapResourceAppender;
import de.vinado.app.playground.wicket.configuration.WicketConfigurer;
import org.apache.wicket.Page;
import org.apache.wicket.application.ComponentInitializationListenerCollection;
import org.apache.wicket.application.ComponentInstantiationListenerCollection;
import org.apache.wicket.core.request.handler.PageProvider;
import org.apache.wicket.core.request.handler.RenderPageRequestHandler;
import org.apache.wicket.csp.CSPDirective;
import org.apache.wicket.csp.CSPDirectiveSrcValue;
import org.apache.wicket.csp.ContentSecurityPolicySettings;
import org.apache.wicket.markup.html.HeaderResponseDecoratorCollection;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.cycle.IRequestCycleListener;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.cycle.RequestCycleListenerCollection;
import org.apache.wicket.request.resource.caching.NoOpResourceCachingStrategy;
import org.apache.wicket.settings.DebugSettings;
import org.apache.wicket.settings.DebugSettings.ClassOutputStrategy;
import org.apache.wicket.settings.MarkupSettings;
import org.apache.wicket.settings.ResourceSettings;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import lombok.Setter;
import lombok.experimental.Accessors;

@Profile("wicket")
@Component
public class WicketApplication extends WebApplication implements ApplicationContextAware {

    @Setter
    @Accessors(fluent = false)
    private ApplicationContext applicationContext;

    @Setter
    private WebjarsSettings webjarsSettings;

    private final List<WicketConfigurer> configurers;

    public WicketApplication(@Autowired(required = false) List<WicketConfigurer> configurers) {
        this.configurers = configurers;
    }

    @Override
    public Class<? extends Page> getHomePage() {
        return PreviewPage.class;
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

        WicketWebjars.install(this, webjarsSettings);

        configurers().forEach(configurer -> configurer.init(this));

        mountPage("error", ExceptionErrorPage.class);
    }

    private void configure(MarkupSettings settings) {
        settings.setDefaultMarkupEncoding(StandardCharsets.UTF_8.name());
        settings.setCompressWhitespace(usesDeploymentConfig());
        settings.setStripComments(usesDeploymentConfig());
        settings.setStripWicketTags(true);
    }

    private void configure(ContentSecurityPolicySettings settings) {
        settings.blocking().strict()
            .add(CSPDirective.STYLE_SRC, CSPDirectiveSrcValue.SELF)
            .add(CSPDirective.IMG_SRC, "data:")
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
        if (usesDevelopmentConfig()) {
            settings.setComponentPathAttributeName("data-wicket-path");
            settings.setOutputMarkupContainerClassNameStrategy(ClassOutputStrategy.HTML_COMMENT);
        }
    }

    private void configure(HeaderResponseDecoratorCollection decorators) {
        decorators.add(new JavaScriptFilteredIntoFooterHeaderResponseDecorator());
    }

    private void configure(RequestCycleListenerCollection listeners) {
        listeners.add(new ExceptionHandler());
    }

    public WebjarsSettings getWebjarsSettings() {
        if (null == webjarsSettings) {
            webjarsSettings = new WebjarsSettings();
        }
        return webjarsSettings;
    }

    private Stream<WicketConfigurer> configurers() {
        return Optional.ofNullable(configurers).stream()
            .flatMap(List::stream);
    }


    private static class ExceptionHandler implements IRequestCycleListener {

        @Override
        public IRequestHandler onException(RequestCycle cycle, Exception ex) {
            ExceptionErrorPage errorPage = new ExceptionErrorPage(ex);
            PageProvider pageProvider = new PageProvider(errorPage);
            return new RenderPageRequestHandler(pageProvider);
        }
    }
}
