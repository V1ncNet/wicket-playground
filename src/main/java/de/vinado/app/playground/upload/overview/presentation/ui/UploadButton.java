package de.vinado.app.playground.upload.overview.presentation.ui;

import de.vinado.app.playground.upload.adapter.app.UploadHandler;
import de.vinado.app.playground.upload.adapter.app.UploadHandlerFactory;
import de.vinado.app.playground.upload.adapter.model.Request;
import de.vinado.app.playground.wicket.Holder;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class UploadButton extends AjaxLink<Request> {

    @SpringBean
    private Holder<UploadHandlerFactory> uploadHandlerFactory;

    public UploadButton(String id, IModel<Request> model) {
        super(id, model);
    }

    @Override
    public void onClick(AjaxRequestTarget target) {
        UploadHandler uploadHandler = uploadHandlerFactory.service().create();
        uploadHandler.handle(getModelObject());
        send(getPage(), Broadcast.BREADTH, new UploadCompletedEvent());
    }


    @Component
    @RequiredArgsConstructor
    static class UploadHandlerFactoryHolder implements Holder<UploadHandlerFactory> {

        @Getter
        private final UploadHandlerFactory service;
    }
}
