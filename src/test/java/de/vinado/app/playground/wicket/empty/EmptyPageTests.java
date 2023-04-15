package de.vinado.app.playground.wicket.empty;

import de.vinado.app.playground.wicket.test.SpringEnabledWicketTestCase;
import org.junit.jupiter.api.Test;

class EmptyPageTests extends SpringEnabledWicketTestCase {

    @Test
    void callingUrl_shouldRenderPage() {
        tester.executeUrl("empty");

        tester.assertRenderedPage(EmptyPage.class);
    }

    @Test
    void renderingPage_shouldRenderPage() {
        tester.startPage(EmptyPage.class);

        tester.assertRenderedPage(EmptyPage.class);
    }
}
