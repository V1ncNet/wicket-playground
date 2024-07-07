package de.vinado.app.playground.upload.overview.presentation.ui;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.model.IModel;

import java.util.UUID;

public class UploadButton extends AjaxLink<UploadableDocuments> {

    public UploadButton(String id, IModel<UploadableDocuments> model) {
        super(id, model);
    }

    @Override
    public void onClick(AjaxRequestTarget target) {
        UUID transferId = UUID.randomUUID();
        getModelObject().forEach(document -> document.getUploadResult().transferId(transferId));
        send(getPage(), Broadcast.BREADTH, new UploadCompletedEvent());
    }
}
