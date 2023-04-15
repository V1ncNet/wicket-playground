package de.vinado.app.playground.wicket;

import lombok.Setter;
import org.apache.wicket.Page;
import org.apache.wicket.application.ComponentInstantiationListenerCollection;
import org.apache.wicket.mock.MockHomePage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class WicketApplication extends WebApplication implements ApplicationContextAware {

    @Setter
    private ApplicationContext applicationContext;

    @Override
    public Class<? extends Page> getHomePage() {
        return MockHomePage.class;
    }

    @Override
    protected void init() {
        super.init();

        ComponentInstantiationListenerCollection componentInstantiationListeners = getComponentInstantiationListeners();
        configure(componentInstantiationListeners);

        mountPages();
    }

    private void configure(ComponentInstantiationListenerCollection listeners) {
        listeners.add(new SpringComponentInjector(this, applicationContext));
    }

    private void mountPages() {
    }
}
