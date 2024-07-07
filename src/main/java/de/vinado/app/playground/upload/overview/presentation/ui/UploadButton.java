package de.vinado.app.playground.upload.overview.presentation.ui;

import de.vinado.app.playground.upload.adapter.app.UploadClientFactory;
import de.vinado.app.playground.upload.client.model.Transfer;
import de.vinado.app.playground.upload.client.model.UploadClient;
import de.vinado.app.playground.upload.client.model.UploadException;
import de.vinado.app.playground.upload.client.model.UploadListener;
import de.vinado.app.playground.wicket.Holder;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;
import java.util.UUID;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

public class UploadButton extends AjaxLink<UploadableDocuments> {

    @SpringBean
    private Holder<UploadClientFactory> uploadClientFactory;

    public UploadButton(String id, IModel<UploadableDocuments> model) {
        super(id, model);
    }

    @Override
    public void onClick(AjaxRequestTarget target) {
        Transfer transfer = new Transfer();
        getModelObject().stream()
            .map(UploadableDocument::getUri)
            .map(Paths::get)
            .forEach(transfer::add);
        UploadClient uploadClient = uploadClientFactory.service().create();
        uploadClient.dispatch(transfer, new UploadListener() {

            @Override
            public void onUploadCompleted(@NonNull UUID transferId) {
                getModelObject().forEach(document -> document.getUploadResult().transferId(transferId));
                send(getPage(), Broadcast.BREADTH, new UploadCompletedEvent());
            }

            @Override
            public void onUploadInterrupted(@NonNull UploadException e) {
            }
        });
    }


    @Component
    @RequiredArgsConstructor
    static class UploadClientFactoryHolder implements Holder<UploadClientFactory> {

        @Getter
        private final UploadClientFactory service;
    }
}
