package de.vinado.app.playground.bootstrap.presentation.ui;

import de.vinado.app.playground.wicket.PlaygroundPage;
import de.vinado.app.playground.wicket.bootstrap.modal.Modal;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;

import static de.vinado.app.playground.wicket.bootstrap.modal.Modal.CloseAction;
import static de.vinado.app.playground.wicket.bootstrap.modal.Modal.SubmitAction;

public class BootstrapPage extends PlaygroundPage {

    private final IModel<ModalForm.Bean> model;
    private final Modal modal;

    public BootstrapPage() {
        this.model = new CompoundPropertyModel<>(new ModalForm.Bean());
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
                    .content(id -> new ModalForm(id, model))
                    .addAction(id -> new CloseAction(id, new ResourceModel("cancel", "Cancel")))
                    .addAction(id -> new SubmitAction(id, new ResourceModel("submit", "Submit")))
                    .show(target);
            }
        });
    }

    private Modal modal(String wicketId) {
        return new Modal(wicketId);
    }
}
