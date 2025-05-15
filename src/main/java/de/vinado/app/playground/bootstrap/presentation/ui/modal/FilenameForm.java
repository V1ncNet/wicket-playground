package de.vinado.app.playground.bootstrap.presentation.ui.modal;

import de.vinado.app.playground.bootstrap.presentation.ui.modal.UploadForm.Data.Upload;
import de.vinado.app.playground.wicket.bootstrap.form.FormControl;
import de.vinado.app.playground.wicket.bootstrap.modal.FormModalPanel;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LambdaModel;
import org.apache.wicket.model.ResourceModel;

public class FilenameForm extends FormModalPanel<Upload> {

    public FilenameForm(String id, IModel<Upload> model) {
        super(id, model);
    }

    @Override
    protected void onSubmit() {
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        IModel<String> filenameModel = LambdaModel.of(getModel(), Upload::fileName, Upload::fileName);
        FilenameFormControl filenameFormControl = new FilenameFormControl("filename", filenameModel);
        filenameFormControl.setRequired(true);
        filenameFormControl.setLabel(new ResourceModel("filename.field.label", "Filename"));
        add(filenameFormControl);
    }


    private static class FilenameFormControl extends FormControl<String> {

        public FilenameFormControl(String id, IModel<String> model) {
            super(id, model);
        }

        @Override
        protected FormComponent<String> control(String wicketId) {
            return new TextField<>(wicketId) {

                @Override
                protected void onComponentTag(ComponentTag tag) {
                    tag.put("type", "text");

                    super.onComponentTag(tag);
                }
            };
        }
    }
}
