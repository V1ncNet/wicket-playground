package de.vinado.app.playground.note.presentation.ui;

import de.vinado.app.playground.wicket.codimd.CodiMd;
import de.vinado.app.playground.wicket.codimd.CodiMdUrlProvider;
import de.vinado.app.playground.wicket.navigation.NavigationItemSupplier;
import de.vinado.app.playground.wicket.test.SpringEnabledWicketTestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.net.URL;
import java.util.UUID;
import java.util.stream.Stream;

import lombok.SneakyThrows;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = NoteConfiguration.class)
class NotePageTests extends SpringEnabledWicketTestCase {

    @MockBean
    private NavigationItemSupplier supplier;

    @MockBean
    private CodiMdUrlProvider urlProvider;

    @BeforeEach
    @SneakyThrows
    void setUp() {
        when(supplier.get()).thenAnswer(i -> Stream.empty());
        when(urlProvider.resolve(any())).thenReturn(randomUrl());
    }

    @SneakyThrows
    private static URL randomUrl() {
        UUID host = UUID.randomUUID();
        return new URL("http://" + host);
    }

    @Test
    void callingUrl_shouldRenderPage() {
        tester.executeUrl("note");

        tester.assertRenderedPage(NotePage.class);
    }

    @Test
    void renderingPage_shouldRenderPage() {
        tester.startPage(NotePage.class);

        tester.assertRenderedPage(NotePage.class);
    }

    @Test
    void renderingPage_shouldRenderCodiMdPad() {
        tester.startPage(NotePage.class);

        tester.assertComponent("navigation:navigation_body:codiMd", CodiMd.class);
    }
}
