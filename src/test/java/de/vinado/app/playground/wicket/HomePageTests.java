package de.vinado.app.playground.wicket;

import de.vinado.app.playground.wicket.test.SpringEnabledWicketTestCase;
import org.junit.jupiter.api.Test;

class HomePageTests extends SpringEnabledWicketTestCase {

    @Test
    void callingUrl_shouldRenderPage() {
        tester.executeUrl("/");

        tester.assertRenderedPage(HomePage.class);
    }

    @Test
    void renderingPage_shouldRenderPage() {
        tester.startPage(HomePage.class);

        tester.assertRenderedPage(HomePage.class);
    }
}
