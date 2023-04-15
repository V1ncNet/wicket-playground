package de.vinado.app.playground.wicket.test;

import de.vinado.app.playground.wicket.WicketApplication;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.tester.WicketTestCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("wicket")
@SpringBootTest(classes = WicketApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public abstract class SpringEnabledWicketTestCase extends WicketTestCase {

    @Autowired
    private WebApplication webApplication;

    @Override
    protected WebApplication newApplication() {
        return webApplication;
    }
}
