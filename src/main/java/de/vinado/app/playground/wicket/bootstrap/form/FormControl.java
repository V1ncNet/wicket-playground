package de.vinado.app.playground.wicket.bootstrap.form;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.feedback.FeedbackMessages;
import org.apache.wicket.feedback.IFeedbackMessageFilter;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.FormComponentPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.cycle.RequestCycle;

import java.io.Serializable;
import java.util.Objects;

import lombok.RequiredArgsConstructor;

public abstract class FormControl<T extends Serializable> extends FormComponentPanel<T> {

    private final FormComponent<T> control;
    private Feedback feedback;

    public FormControl(String id) {
        this(id, null);
    }

    public FormControl(String id, IModel<T> model) {
        super(id, model);

        this.control = control("control");
    }

    protected abstract FormComponent<T> control(String wicketId);

    protected Component label(String wicketId, FormComponent<T> control) {
        return new FormLabel(wicketId, this)
            .references(control);
    }

    protected Feedback feedback(String wicketId) {
        return new Feedback(wicketId, this);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        control.setModel(new Model<T>());

        add(control);
        add(label("label", control));
        add(feedback = feedback("feedback"));

        feedback.setOutputMarkupId(true);
    }

    @Override
    public void convertInput() {
        setConvertedInput(control.getConvertedInput());
    }

    @Override
    protected void onBeforeRender() {
        super.onBeforeRender();

        control.setModelObject(getModelObject());
    }

    @Override
    protected void onValid() {
        control.add(AttributeModifier.replace("class", "form-control"));
        updateFeedback();
    }

    @Override
    protected void onInvalid() {
        FeedbackMessages messages = getFeedbackMessages();
        ErrorMessageFilter filter = new ErrorMessageFilter(this);

        if (messages.hasMessage(filter)) {
            control.add(AttributeModifier.replace("class", "form-control is-invalid"));
        } else {
            control.add(AttributeModifier.replace("class", "form-control is-valid"));
        }

        updateFeedback();
    }

    private void updateFeedback() {
        control.add(AttributeModifier.replace("aria-describedby", feedback.getMarkupId()));
        RequestCycle.get().find(AjaxRequestTarget.class).ifPresent(target -> target.add(feedback));
    }


    @RequiredArgsConstructor
    private static class ErrorMessageFilter implements IFeedbackMessageFilter {

        private final FormComponent<?> reporter;

        @Override
        public boolean accept(FeedbackMessage message) {
            return Objects.equals(reporter, message.getReporter())
                && FeedbackMessage.ERROR == message.getLevel();
        }
    }
}
