package de.vinado.app.playground.document.presentation.ui;

import de.vinado.app.playground.wicket.test.SpringEnabledWicketTestCase;
import org.junit.jupiter.api.Test;

class PreviewPageTests extends SpringEnabledWicketTestCase {

    @Test
    void callingUrl_shouldRenderPage() {
        tester.executeUrl("preview");

        tester.assertRenderedPage(PreviewPage.class);
    }

    @Test
    void renderingPage_shouldRenderPage() {
        tester.startPage(PreviewPage.class);

        tester.assertRenderedPage(PreviewPage.class);
    }

    @Test
    void renderingPage_shouldNotRenderPreview() {
        tester.startPage(PreviewPage.class);

        tester.assertInvisible("navigation:navigation_body:preview");
    }
}
