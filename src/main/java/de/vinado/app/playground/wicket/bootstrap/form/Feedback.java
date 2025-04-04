package de.vinado.app.playground.wicket.bootstrap.form;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.feedback.FeedbackMessagesModel;
import org.apache.wicket.feedback.IFeedback;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Feedback extends Panel implements IFeedback {

    private final Messages messages;

    public Feedback(String id, FormComponent<?> formComponent) {
        super(id);

        this.messages = messages("messages", formComponent);
    }

    private Messages messages(String wicketId, FormComponent<?> formComponent) {
        FeedbackMessagesModel model = feedbackMessagesModel();
        model.setFilter(message -> Objects.equals(formComponent, message.getReporter()));
        return new Messages(wicketId, model);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        setOutputMarkupPlaceholderTag(true);

        messages.setVersioned(false);

        add(messages);
    }

    @Override
    protected void onConfigure() {
        super.onConfigure();

        if (!hasAnyMessage()) {
            setVisible(false);
            return;
        }

        FeedbackMessage feedback = getCurrentMessages().get(0);
        String cssClass = switch (feedback.getLevel()) {
            case FeedbackMessage.INFO, FeedbackMessage.SUCCESS -> "valid-feedback";
            case FeedbackMessage.ERROR, FeedbackMessage.FATAL -> "invalid-feedback";
            default -> "form-text";
        };

        add(AttributeModifier.replace("class", cssClass));
        setVisible(true);
    }

    @Override
    public boolean isVersioned() {
        return false;
    }

    public final boolean hasAnyMessage() {
        return hasAnyMessage(FeedbackMessage.UNDEFINED);
    }

    public final boolean hasAnyMessage(int level) {
        return getCurrentMessages().stream()
            .anyMatch(message -> message.isLevel(level));
    }

    protected final List<FeedbackMessage> getCurrentMessages() {
        List<? extends FeedbackMessage> messages = this.messages.getModelObject();
        return Collections.unmodifiableList(messages);
    }

    protected FeedbackMessagesModel feedbackMessagesModel() {
        return new FeedbackMessagesModel(this);
    }


    private class Messages extends ListView<FeedbackMessage> {

        public Messages(String id, FeedbackMessagesModel model) {
            super(id, model);
        }

        @Override
        protected IModel<FeedbackMessage> getListItemModel(IModel<? extends List<FeedbackMessage>> listViewModel, int index) {
            return () -> {
                List<DeduplifiableFeedbackMessage> messages = listViewModel.getObject().stream()
                    .map(DeduplifiableFeedbackMessage::new)
                    .distinct()
                    .toList();
                return index >= messages.size() ? null : messages.get(index);
            };
        }

        @Override
        protected void populateItem(ListItem<FeedbackMessage> listItem) {
            FeedbackMessage feedback = listItem.getModelObject();

            if (null == feedback) {
                listItem.add(new EmptyPanel("message"));
                return;
            }

            feedback.markRendered();
            Component message = message("message", feedback);
            listItem.add(message);
        }

        private Component message(String wicketId, FeedbackMessage feedback) {
            Serializable message = feedback.getMessage();
            Label label = new Label(wicketId, message);
            label.setEscapeModelStrings(Feedback.this.getEscapeModelStrings());
            return label;
        }


        private static class DeduplifiableFeedbackMessage extends FeedbackMessage {

            public DeduplifiableFeedbackMessage(FeedbackMessage subject) {
                super(subject.getReporter(), subject.getMessage(), subject.getLevel());
            }

            @Override
            public boolean equals(Object obj) {
                if (obj == null || getClass() != obj.getClass()) return false;
                DeduplifiableFeedbackMessage that = (DeduplifiableFeedbackMessage) obj;
                return this.getLevel() == that.getLevel()
                    && this.isRendered() == that.isRendered()
                    && Objects.equals(this.getMessage(), that.getMessage())
                    && Objects.equals(this.getReporter(), that.getReporter());
            }

            @Override
            public int hashCode() {
                int result = getLevel();
                result = 31 * result + Objects.hashCode(getMessage());
                result = 31 * result + Objects.hashCode(getReporter());
                result = 31 * result + Boolean.hashCode(isRendered());
                return result;
            }
        }
    }
}
