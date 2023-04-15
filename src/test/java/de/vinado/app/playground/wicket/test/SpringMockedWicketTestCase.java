package de.vinado.app.playground.wicket.test;

import de.vinado.app.playground.wicket.WicketApplication;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.tester.WicketTestCase;
import org.springframework.context.ApplicationContext;

import static org.mockito.Mockito.mock;

public abstract class SpringMockedWicketTestCase extends WicketTestCase {

    @Override
    protected WebApplication newApplication() {
        WicketApplication webApplication = new WicketApplication();
        webApplication.setApplicationContext(mock(ApplicationContext.class));
        return webApplication;
    }
}
