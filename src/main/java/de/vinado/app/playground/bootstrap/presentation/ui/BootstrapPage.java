package de.vinado.app.playground.bootstrap.presentation.ui;

import de.vinado.app.playground.wicket.PlaygroundPage;
import de.vinado.app.playground.wicket.bootstrap.modal.Modal;

public class BootstrapPage extends PlaygroundPage {

    private final Modal modal;

    public BootstrapPage() {
        this.modal = modal("modal");
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        queue(modal);
    }

    private Modal modal(String wicketId) {
        return new Modal(wicketId);
    }
}
