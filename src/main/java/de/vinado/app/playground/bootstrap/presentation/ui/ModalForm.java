package de.vinado.app.playground.bootstrap.presentation.ui;

import de.vinado.app.playground.wicket.bootstrap.form.Feedback;
import de.vinado.app.playground.wicket.bootstrap.form.FormLabel;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.feedback.FeedbackMessages;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.validation.validator.RangeValidator;
import org.apache.wicket.validation.validator.StringValidator;

import java.io.Serializable;
import java.util.Objects;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ModalForm extends GenericPanel<ModalForm.Bean> {

    private final Form<Bean> form;

    private FormComponent<String> messageTextField;
    private WebMarkupContainer messageTextFieldFeedback;
    private FormComponent<Integer> amountNumberField;
    private WebMarkupContainer amountNumberFieldFeedback;

    public ModalForm(String id, IModel<Bean> model) {
        super(id, model);

        this.form = form("form");
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        queue(form);
        queue(messageTextField = messageTextField("message"));
        queue(messageTextFieldLabel("messageLabel"));
        queue(messageTextFieldFeedback = messageTextFieldFeedback("messageFeedback"));
        queue(amountNumberField = amountNumberField("amount"));
        queue(amountNumberFieldLabel("amountLabel"));
        queue(amountNumberFieldFeedback = amountNumberFieldFeedback("amountFeedback"));
    }

    protected Form<Bean> form(String wicketId) {
        return new Form<>(wicketId, ModalForm.this.getModel()) {

            @Override
            protected void onValidate() {
                RequestCycle.get().find(AjaxRequestTarget.class)
                    .ifPresent(target -> visitFormComponents((formComponent, visit) -> {
                        if (formComponent.isRequired() || !formComponent.getValidators().isEmpty()) {
                            target.add(formComponent);
                        }
                    }));
            }
        };
    }

    protected FormComponent<String> messageTextField(String wicketId) {
        IModel<String> label = messageTextFieldLabelModel();
        TextField<String> textField = new TextField<>(wicketId) {

            @Override
            protected void onValid() {
                handleValid(this, messageTextFieldFeedback);
            }

            @Override
            protected void onInvalid() {
                handleInvalid(this, messageTextFieldFeedback);
            }
        };
        textField.setLabel(label);
        textField.setRequired(true);
        textField.add(StringValidator.minimumLength(1));
        return textField;
    }

    protected IModel<String> messageTextFieldLabelModel() {
        return new ResourceModel("message", "Message");
    }

    protected WebMarkupContainer messageTextFieldLabel(String wicketId) {
        return new FormLabel(wicketId, messageTextField);
    }

    protected WebMarkupContainer messageTextFieldFeedback(String wicketId) {
        return new Feedback(wicketId, messageTextField);
    }

    protected FormComponent<Integer> amountNumberField(String wicketId) {
        IModel<String> label = amountNumberFieldLabelModel();
        NumberTextField<Integer> numberField = new NumberTextField<>(wicketId) {

            @Override
            protected void onValid() {
                handleValid(this, amountNumberFieldFeedback);
            }

            @Override
            protected void onInvalid() {
                handleInvalid(this, amountNumberFieldFeedback);
            }
        };
        numberField.setType(Integer.class);
        numberField.setLabel(label);
        numberField.add(RangeValidator.range(0, Integer.MAX_VALUE));
        return numberField;
    }

    protected IModel<String> amountNumberFieldLabelModel() {
        return new ResourceModel("amount", "Amount");
    }

    protected WebMarkupContainer amountNumberFieldLabel(String wicketId) {
        return new FormLabel(wicketId, amountNumberField);
    }

    protected WebMarkupContainer amountNumberFieldFeedback(String wicketId) {
        return new Feedback(wicketId, amountNumberField);
    }

    private void handleValid(FormComponent<?> formComponent, WebMarkupContainer feedback) {
        formComponent.add(AttributeModifier.replace("class", "form-control"));
        updateFeedback(formComponent, feedback);
    }

    private void handleInvalid(FormComponent<?> formComponent, WebMarkupContainer feedback) {
        FeedbackMessages messages = formComponent.getFeedbackMessages();
        if (messages.hasMessage(message -> Objects.equals(formComponent, message.getReporter())
            && FeedbackMessage.ERROR == message.getLevel())) {
            formComponent.add(AttributeModifier.replace("class", "form-control is-invalid"));
        } else {
            formComponent.add(AttributeModifier.replace("class", "form-control is-valid"));
        }

        updateFeedback(formComponent, feedback);
    }

    private void updateFeedback(FormComponent<?> formComponent, WebMarkupContainer feedback) {
        formComponent.add(AttributeModifier.replace("aria-describedby", feedback.getMarkupId()));
        RequestCycle.get().find(AjaxRequestTarget.class).ifPresent(target -> target.add(feedback));
    }


    @Data
    public static class Bean implements Serializable {

        private String message;

        private Integer amount = 0;
    }
}
