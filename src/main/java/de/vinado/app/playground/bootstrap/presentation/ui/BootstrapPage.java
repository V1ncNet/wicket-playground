package de.vinado.app.playground.bootstrap.presentation.ui;

import de.vinado.app.playground.wicket.PlaygroundPage;
import de.vinado.app.playground.wicket.bootstrap.modal.Modal;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.model.Model;

public class BootstrapPage extends PlaygroundPage {

    private final Modal modal;

    public BootstrapPage() {
        this.modal = modal("modal");
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        queue(modal);
        queue(new AjaxLink<>("openButton") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                modal
                    .title(Model.of("Bootstrap Modal"))
                    .content(id -> new EmptyPanel(id))
                    .addCloseAction()
                    .show(target);
            }
        });
    }

    private Modal modal(String wicketId) {
        return new Modal(wicketId);
    }
}
