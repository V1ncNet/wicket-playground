package de.vinado.app.playground.wicket.test;

import de.vinado.app.playground.wicket.WicketApplication;
import de.vinado.app.playground.wicket.configuration.PlaygroundWicketConfigurer;
import org.apache.wicket.Page;
import org.apache.wicket.mock.MockHomePage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.tester.WicketTestCase;
import org.springframework.context.ApplicationContext;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.mock;

public abstract class SpringMockedWicketTestCase extends WicketTestCase {

    @Override
    protected WebApplication newApplication() {
        WicketApplication webApplication = new WicketApplication() {

            @Override
            public Class<? extends Page> getHomePage() {
                return MockHomePage.class;
            }
        };
        webApplication.setApplicationContext(mock(ApplicationContext.class));
        return webApplication;
    }

    protected List<PlaygroundWicketConfigurer> configurers() {
        return Collections.emptyList();
    }
}
