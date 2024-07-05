package de.vinado.app.playground.wicket.test;

import de.vinado.app.playground.wicket.WicketApplication;
import de.vinado.app.playground.wicket.configuration.WicketConfigurer;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.tester.WicketTestCase;
import org.springframework.context.ApplicationContext;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

public abstract class SpringMockedWicketTestCase extends WicketTestCase {

    @Override
    protected WebApplication newApplication() {
        WicketApplication webApplication = new WicketApplication(configurers());
        webApplication.setApplicationContext(mock(ApplicationContext.class));
        return webApplication;
    }

    protected List<WicketConfigurer> configurers() {
        return Collections.emptyList();
    }
}
