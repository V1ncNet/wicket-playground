package de.vinado.app.playground.wicket.bootstrap;

import de.agilecoders.wicket.core.Bootstrap;
import de.agilecoders.wicket.core.settings.BootstrapSettings;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.icon.FontAwesome5CssReference;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.icon.FontAwesomeSettings;
import de.agilecoders.wicket.jquery.settings.WicketJquerySelectorsSettings;
import de.agilecoders.wicket.webjars.settings.WebjarsSettings;
import de.vinado.app.playground.wicket.JavaScriptFilteredIntoFooterHeaderResponseDecorator;
import de.vinado.app.playground.wicket.WicketApplication;
import de.vinado.app.playground.wicket.bootstrap.home.HomePage;
import de.vinado.app.playground.wicket.configuration.WicketConfigurer;
import lombok.Setter;
import org.apache.wicket.Page;
import org.apache.wicket.csp.CSPDirective;
import org.apache.wicket.csp.CSPDirectiveSrcValue;
import org.apache.wicket.csp.ContentSecurityPolicySettings;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Profile("wicket & bootstrap")
@Component
public class BootstrapWicketApplication extends WicketApplication {

    @Setter
    private BootstrapSettings bootstrapSettings;

    @Setter
    private WicketJquerySelectorsSettings jquerySelectorsSettings;

    @Setter
    private FontAwesomeSettings fontAwesomeSettings;

    public BootstrapWicketApplication(List<? extends WicketConfigurer> configurers) {
        super(configurers);
    }

    @Override
    public Class<? extends Page> getHomePage() {
        return HomePage.class;
    }

    @Override
    protected void init() {
        super.init();

        BootstrapSettings bootstrapSettings = getBootstrapSettings();
        configure(bootstrapSettings);

        WebjarsSettings webjarsSettings = getWebjarsSettings();
        configure(webjarsSettings);

        WicketJquerySelectorsSettings jquerySelectorSettings = getJquerySelectorSettings();
        configure(jquerySelectorSettings);

        Bootstrap.Builder bootstrapBuilder = Bootstrap.builder()
            .withBootstrapSettings(bootstrapSettings)
            .withWebjarsSettings(webjarsSettings)
            .withJQuerySelectorSettings(jquerySelectorSettings);
        configure(bootstrapBuilder);

        bootstrapBuilder.install(this);

        FontAwesomeSettings fontAwesomeSettings = getFontAwesomeSettings();
        configure(fontAwesomeSettings);

        FontAwesomeSettings.set(this, fontAwesomeSettings);
    }

    @Override
    protected void configure(ContentSecurityPolicySettings settings) {
        settings.blocking().strict()
            .add(CSPDirective.STYLE_SRC, CSPDirectiveSrcValue.SELF)
            .add(CSPDirective.IMG_SRC, "data:")
        ;
    }

    @Override
    protected void configure(WebjarsSettings settings) {
        super.configure(settings);

        settings.useCdnResources(false);
    }

    protected void configure(BootstrapSettings settings) {
        settings.setJsResourceFilterName(JavaScriptFilteredIntoFooterHeaderResponseDecorator.FILTER_NAME);
        settings.setDeferJavascript(true);
    }

    protected void configure(WicketJquerySelectorsSettings settings) {
    }

    protected void configure(Bootstrap.Builder builder) {
    }

    protected void configure(FontAwesomeSettings settings) {
        settings.setCssResourceReference(FontAwesome5CssReference.instance());
    }

    public BootstrapSettings getBootstrapSettings() {
        if (null == bootstrapSettings) {
            bootstrapSettings = new BootstrapSettings();
        }
        return bootstrapSettings;
    }

    public WicketJquerySelectorsSettings getJquerySelectorSettings() {
        if (null == jquerySelectorsSettings) {
            jquerySelectorsSettings = new WicketJquerySelectorsSettings();
        }
        return jquerySelectorsSettings;
    }

    public FontAwesomeSettings getFontAwesomeSettings() {
        if (null == fontAwesomeSettings) {
            fontAwesomeSettings = new FontAwesomeSettings();
        }
        return fontAwesomeSettings;
    }
}
