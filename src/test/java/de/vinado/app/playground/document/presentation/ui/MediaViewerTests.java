package de.vinado.app.playground.document.presentation.ui;

import de.vinado.app.playground.wicket.test.SpringEnabledWicketTestCase;
import org.junit.jupiter.api.Test;

class MediaViewerTests extends SpringEnabledWicketTestCase {

    private static final String WICKET_ID = "viewer";

    @Test
    void startingWithNullModel_shouldNotRenderComponent() {
        MediaViewer panel = new MediaViewer(WICKET_ID);

        tester.startComponentInPage(panel);

        tester.assertInvisible(WICKET_ID);
    }
}
