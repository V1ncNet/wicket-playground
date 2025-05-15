package de.vinado.app.playground.wicket.bootstrap.toast;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.feedback.FeedbackMessagesModel;
import org.apache.wicket.feedback.IFeedback;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.cycle.RequestCycle;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class Toaster extends Panel implements IFeedback {

    private final Toasts toasts;

    public Toaster(String id) {
        super(id);

        FeedbackMessagesModel model = feedbackMessagesModel();
        this.toasts = new Toasts("toasts", model);
    }

    protected FeedbackMessagesModel feedbackMessagesModel() {
        return new FeedbackMessagesModel(this);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        setOutputMarkupId(true);

        toasts.setVersioned(false);

        add(toasts);
    }

    @Override
    protected void onComponentTag(ComponentTag tag) {
        super.onComponentTag(tag);

        tag.put("class", "toast-container position-static");
    }

    @Override
    public boolean isVersioned() {
        return false;
    }

    public void update(AjaxRequestTarget target) {
        if (hasAnyMessage()) {
            target.add(this);
        }
    }

    public boolean hasAnyMessage() {
        return hasAnyMessage(FeedbackMessage.UNDEFINED);
    }

    public boolean hasAnyMessage(int level) {
        return getCurrentMessages().stream()
            .anyMatch(message -> message.isLevel(level));
    }

    private List<FeedbackMessage> getCurrentMessages() {
        List<FeedbackMessage> messages = toasts.getModelObject();
        return Collections.unmodifiableList(messages);
    }


    private class Toasts extends ListView<FeedbackMessage> {

        public Toasts(String id, FeedbackMessagesModel model) {
            super(id, model);
        }

        @Override
        protected IModel<FeedbackMessage> getListItemModel(IModel<? extends List<FeedbackMessage>> listViewModel,
                                                           int index) {
            return () -> {
                if (index < listViewModel.getObject().size()) {
                    return listViewModel.getObject().get(index);
                }

                return null;
            };
        }

        @Override
        protected void populateItem(ListItem<FeedbackMessage> item) {
            FeedbackMessage message = item.getModelObject();
            message.markRendered();
            Toast toast = toast("toast", message);
            item.add(toast);
//            RequestCycle.get().find(AjaxRequestTarget.class).ifPresent(toast::show);
        }
    }

    protected Toast toast(String wicketId, FeedbackMessage message) {
        IModel<Serializable> model = message::getMessage;
        return new Toast(wicketId, model);
    }
}
