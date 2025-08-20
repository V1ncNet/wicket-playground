package de.vinado.app.playground.bootstrap.presentation.ui.modal;

import de.vinado.app.playground.wicket.bootstrap.BootstrapPage;
import de.vinado.app.playground.wicket.bootstrap.modal.Modal;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;

public class UploadFormDialog extends GenericPanel<UploadForm.Data> {

    private final Modal modal;

    private final UploadForm form;

    public UploadFormDialog() {
        super(Modal.CONTENT_WICKET_ID, model());

        this.modal = BootstrapPage.get().getModal();
        this.form = new UploadForm("form", getModel());
    }

    private static IModel<UploadForm.Data> model() {
        UploadForm.Data data = new UploadForm.Data();
        return new CompoundPropertyModel<>(data);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(form);
    }

    public AjaxLink<Void> showButton(String wicketId) {
        return new AjaxLink<>(wicketId) {

            @Override
            public void onClick(AjaxRequestTarget target) {
                modal
                    .title(Model.of("File Upload Demo"))
                    .content(UploadFormDialog.this)
                    .addAction(id -> new Modal.CloseAction(id, new ResourceModel("cancel", "Cancel")))
                    .addAction(id -> new Modal.SubmitAction(id, new ResourceModel("submit", "Submit")))
                    .show(target);
            }
        };
    }
}
