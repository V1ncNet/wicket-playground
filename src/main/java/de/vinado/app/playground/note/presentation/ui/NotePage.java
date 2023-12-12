package de.vinado.app.playground.note.presentation.ui;

import de.vinado.app.playground.wicket.PlaygroundPage;
import de.vinado.app.playground.wicket.codimd.CodiMd;
import de.vinado.app.playground.wicket.codimd.Note;
import org.apache.wicket.model.IModel;

import java.util.UUID;

public class NotePage extends PlaygroundPage {

    private static final UUID NOTE_ID = UUID.fromString("595b2c44-34eb-4a90-a6ad-0645199b316c");

    @Override
    protected void onInitialize() {
        super.onInitialize();

        queue(codiMd("codiMd"));
    }

    private CodiMd codiMd(String wicketId) {
        return new CodiMd(wicketId, model());
    }

    private IModel<Note> model() {
        return () -> new Note(NOTE_ID);
    }
}
