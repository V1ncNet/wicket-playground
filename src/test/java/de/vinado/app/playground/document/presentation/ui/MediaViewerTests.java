package de.vinado.app.playground.document.presentation.ui;

import de.vinado.app.playground.wicket.test.SpringEnabledWicketTestCase;
import org.apache.wicket.model.Model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.net.URI;
import java.net.URL;
import java.util.UUID;

import lombok.SneakyThrows;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class MediaViewerTests extends SpringEnabledWicketTestCase {

    private static final String WICKET_ID = "viewer";

    @MockitoBean
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
    void startingWithNullModel_shouldNotRenderComponent() {
        MediaViewer panel = new MediaViewer(WICKET_ID);

        tester.startComponentInPage(panel);

        tester.assertInvisible(WICKET_ID);
    }

    @Test
    void startingWithEmptyModel_shouldNotRenderComponent() {
        MediaViewer panel = new MediaViewer(WICKET_ID, Model.of());

        tester.startComponentInPage(panel);

        tester.assertInvisible(WICKET_ID);
    }

    @Test
    void startingWithRandomDocument_shouldRenderComponent() {
        MediaViewer panel = new MediaViewer(WICKET_ID, this::randomDocument);

        tester.startComponentInPage(panel);

        tester.assertComponent(WICKET_ID, MediaViewer.class);
    }

    private ViewableDocument randomDocument() {
        URI uri = URI.create(UUID.randomUUID().toString());
        return new ViewableDocument(uri);
    }
}
