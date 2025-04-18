package de.vinado.app.playground.bootstrap.presentation.ui;

import de.vinado.app.playground.bootstrap.presentation.ui.modal.DemoDto;
import de.vinado.app.playground.bootstrap.presentation.ui.modal.DemoModalDialogForm;
import de.vinado.app.playground.wicket.PlaygroundPage;
import de.vinado.app.playground.wicket.bootstrap.BootstrapPage;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;

import static de.vinado.app.playground.wicket.bootstrap.modal.Modal.CloseAction;
import static de.vinado.app.playground.wicket.bootstrap.modal.Modal.SubmitAction;

public class BootstrapComponentsPage extends PlaygroundPage {

    @Override
    protected void onInitialize() {
        super.onInitialize();

        queue(demoFormModalDialogButton("demoFormModalDialogButton"));
    }

    private AjaxLink<Object> demoFormModalDialogButton(String wicketId) {
        IModel<DemoDto> model = new CompoundPropertyModel<>(new DemoDto());
        return new AjaxLink<>(wicketId) {

            @Override
            public void onClick(AjaxRequestTarget target) {
                BootstrapPage.get().modal()
                    .title(Model.of("Bootstrap Modal Dialog Demo"))
                    .content(id -> new DemoModalDialogForm(id, model))
                    .addAction(id -> new CloseAction(id, new ResourceModel("cancel", "Cancel")))
                    .addAction(id -> new SubmitAction(id, new ResourceModel("submit", "Submit")))
                    .show(target);
            }
        };
    }
}
