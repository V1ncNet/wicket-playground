package de.vinado.app.playground.document.presentation.ui;

import de.vinado.app.playground.wicket.test.SpringEnabledWicketTestCase;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.net.URL;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = PreviewConfiguration.class)
class PreviewPageTests extends SpringEnabledWicketTestCase {

    @MockBean
    private PreviewUrlProvider previewUrlProvider;

    @BeforeEach
    @SneakyThrows
    void setUp() {
        when(previewUrlProvider.resolve(any())).thenReturn(randomUrl());
    }

    @SneakyThrows
    private static URL randomUrl() {
        UUID host = UUID.randomUUID();
        return new URL("http://" + host);
    }

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
    void renderingPage_shouldRenderPreview() {
        tester.startPage(PreviewPage.class);

        tester.assertComponent("navigation:navigation_body:preview", MediaViewer.class);
    }
}
