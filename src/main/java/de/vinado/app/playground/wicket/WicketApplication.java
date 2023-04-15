package de.vinado.app.playground.wicket;

import de.agilecoders.wicket.webjars.WicketWebjars;
import de.agilecoders.wicket.webjars.settings.WebjarsSettings;
import lombok.Setter;
import org.apache.wicket.Page;
import org.apache.wicket.application.ComponentInstantiationListenerCollection;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

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

        ComponentInstantiationListenerCollection componentInstantiationListeners = getComponentInstantiationListeners();
        configure(componentInstantiationListeners);

        WebjarsSettings webjarsSettings = getWebjarsSettings();
        configure(webjarsSettings);

        mountPages();

        WicketWebjars.install(this, webjarsSettings);
    }

    private void configure(ComponentInstantiationListenerCollection listeners) {
        listeners.add(new SpringComponentInjector(this, applicationContext));
    }

    private void configure(WebjarsSettings settings) {
        settings.useCdnResources(false);
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
