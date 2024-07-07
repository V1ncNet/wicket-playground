package de.vinado.app.playground.upload.overview.presentation.ui;

import de.vinado.app.playground.upload.adapter.app.UploadHandler;
import de.vinado.app.playground.upload.adapter.app.UploadHandlerFactory;
import de.vinado.app.playground.upload.adapter.model.Request;
import de.vinado.app.playground.wicket.Holder;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AbstractAjaxTimerBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.stereotype.Component;

import java.time.Duration;

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
        enableUpdates(target);
        UploadHandler uploadHandler = uploadHandlerFactory.service().create();
        uploadHandler.handle(getModelObject());
        send(getPage(), Broadcast.BREADTH, new UploadCompletedEvent());
    }

    private void enableUpdates(AjaxRequestTarget target) {
        add(new UploadProgressBehavior(Duration.ofMillis(500)));
        target.add(this);
    }


    private class UploadProgressBehavior extends AbstractAjaxTimerBehavior {

        public UploadProgressBehavior(Duration pollingInterval) {
            super(pollingInterval);
        }

        @Override
        protected void onBind() {
            super.onBind();

            add(AttributeModifier.append("disabled", "true"));
        }

        @Override
        protected void onTimer(AjaxRequestTarget target) {
            send(getPage(), Broadcast.BREADTH, new UploadCompletedEvent());
            if (getModelObject().isCompleted()) {
                add(AttributeModifier.remove("disabled"));
                stop(target);
                target.add(UploadButton.this);
            }
        }
    }

    @Component
    @RequiredArgsConstructor
    static class UploadHandlerFactoryHolder implements Holder<UploadHandlerFactory> {

        @Getter
        private final UploadHandlerFactory service;
    }
}
