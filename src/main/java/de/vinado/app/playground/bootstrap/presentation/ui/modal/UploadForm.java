package de.vinado.app.playground.bootstrap.presentation.ui.modal;

import de.vinado.app.playground.wicket.bootstrap.form.BaseFormControl;
import de.vinado.app.playground.wicket.bootstrap.form.Form;
import de.vinado.app.playground.wicket.bootstrap.modal.FormModalPanel;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LambdaModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.validation.ValidationError;
import org.springframework.util.MimeType;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

public class UploadForm extends FormModalPanel<UploadForm.Data> {

    public UploadForm(String id, IModel<UploadForm.Data> model) {
        super(id, model);
    }

    @Override
    protected void configure(Form<Data> form) {
        form.setMultiPart(true);
    }

    @Override
    protected void onSubmit() {
        Data.Upload upload = getModelObject().upload();
        Uploaded event = new Uploaded(upload);
        send(getPage(), Broadcast.DEPTH, event);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        IModel<Data.Upload> uploadModel = LambdaModel.of(getModel(), Data::upload, Data::upload);
        UploadFormControl upload = new UploadFormControl("upload", uploadModel);
        upload.setRequired(true);
        upload.setLabel(new ResourceModel("upload.field.label", "Upload"));
        add(upload);
    }


    @Slf4j
    private static class UploadFormControl extends BaseFormControl<List<FileUpload>, Data.Upload> {

        public UploadFormControl(String id, IModel<Data.Upload> model) {
            super(id, model);
        }

        @Override
        protected FileUploadField control(String wicketId) {
            return new FileUploadField(wicketId) {

                @Override
                protected void onComponentTag(ComponentTag tag) {
                    tag.put("type", "file");

                    super.onComponentTag(tag);
                }
            };
        }

        @Override
        public void convertInput() {
            try {
                List<FileUpload> fileUploads = control().getConvertedInput();
                if (null == fileUploads || fileUploads.isEmpty()) {
                    setConvertedInput(null);
                    return;
                }

                FileUpload fileUpload = fileUploads.get(0);
                File file = fileUpload.writeToTempFile();
                Data.Upload upload = new Data.Upload()
                    .contentType(MimeType.valueOf(fileUpload.getContentType()))
                    .fileName(fileUpload.getClientFileName())
                    .size(fileUpload.getSize())
                    .file(file);

                setConvertedInput(upload);
            } catch (Exception e) {
                log.error("Cannot convert file upload", e);
                String message = getString("upload.error.convert", null, "An unexpected error occurred.");
                error(new ValidationError(message));
            }
        }
    }


    @lombok.Data
    public static class Data implements Serializable {

        private Upload upload = new Upload();


        @lombok.Data
        public static class Upload implements Serializable {

            private MimeType contentType;
            private String fileName;
            private long size;
            private File file;
        }
    }

    public record Uploaded(Data.Upload payload) {
    }
}
