package de.vinado.app.playground.bootstrap.presentation.ui;

import de.vinado.app.playground.bootstrap.presentation.ui.modal.DemoForm;
import de.vinado.app.playground.bootstrap.presentation.ui.modal.UploadFormDialog;
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

        UploadFormDialog uploadFormDialog = new UploadFormDialog();
        queue(uploadFormDialog.showButton("showUploadFormDialogButton"));
    }

    private AjaxLink<Object> demoFormModalDialogButton(String wicketId) {
        IModel<DemoForm.Data> model = new CompoundPropertyModel<>(new DemoForm.Data());
        return new AjaxLink<>(wicketId) {

            @Override
            public void onClick(AjaxRequestTarget target) {
                BootstrapPage.get().modal()
                    .title(Model.of("Bootstrap Modal Dialog Demo"))
                    .content(id -> new DemoForm(id, model))
                    .addAction(id -> new CloseAction(id, new ResourceModel("cancel", "Cancel")))
                    .addAction(id -> new SubmitAction(id, new ResourceModel("submit", "Submit")))
                    .show(target);
            }
        };
    }
}
