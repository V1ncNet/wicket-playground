package de.vinado.app.playground.document.presentation.ui;

import de.vinado.app.playground.wicket.PlaygroundPage;

public class PreviewPage extends PlaygroundPage {

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(preview("preview"));
    }

    private MediaViewer preview(String wicketId) {
        return new MediaViewer(wicketId);
    }
}
