package de.vinado.app.playground.wicket.codimd;

import de.vinado.app.playground.wicket.test.SpringEnabledWicketTestCase;
import lombok.SneakyThrows;
import org.apache.wicket.model.Model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.net.URL;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class CodiMdTests extends SpringEnabledWicketTestCase {

    private static final String WICKET_ID = "pad";

    @MockBean
    private CodiMdUrlProvider urlProvider;

    @BeforeEach
    @SneakyThrows
    void setUp() {
        when(urlProvider.resolve(any())).thenReturn(randomUrl());
    }

    @SneakyThrows
    private static URL randomUrl() {
        UUID host = UUID.randomUUID();
        return new URL("http://" + host);
    }

    @Test
    void startingWithNullModel_shouldNotRenderComponent() {
        CodiMd panel = new CodiMd(WICKET_ID, null);

        tester.startComponentInPage(panel);

        tester.assertInvisible(WICKET_ID);
    }

    @Test
    void startingWithEmptyModel_shouldNotRenderComponent() {
        CodiMd panel = new CodiMd(WICKET_ID, Model.of());

        tester.startComponentInPage(panel);

        tester.assertInvisible(WICKET_ID);
    }

    @Test
    void startingWithRandomNoteId_shouldRenderComponent() {
        CodiMd panel = new CodiMd(WICKET_ID, this::randomNote);

        tester.startComponentInPage(panel);

        tester.assertComponent(WICKET_ID, CodiMd.class);
    }

    private Note randomNote() {
        UUID id = UUID.randomUUID();
        return new Note(id);
    }
}
