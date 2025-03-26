package de.vinado.app.playground.bootstrap.presentation.ui;

import de.vinado.app.playground.wicket.bootstrap.form.FormControl;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.ComponentTag;
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

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ModalForm extends GenericPanel<ModalForm.Bean> {

    private final Form<Bean> form;

    public ModalForm(String id, IModel<Bean> model) {
        super(id, model);

        this.form = form("form");
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        queue(form);
        queue(messageInput("message"));
        queue(amountInput("amount"));
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

    protected FormControl<String> messageInput(String wicketId) {
        IModel<String> label = messageTextFieldLabelModel();
        FormControl<String> textField = new FormControl<>(wicketId) {

            @Override
            protected FormComponent<String> control(String wicketId) {
                return new TextField<>(wicketId);
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

    protected FormComponent<Integer> amountInput(String wicketId) {
        IModel<String> label = amountNumberFieldLabelModel();
        FormControl<Integer> numberField = new FormControl<>(wicketId) {

            @Override
            protected FormComponent<Integer> control(String wicketId) {
                return new NumberTextField<>(wicketId, Integer.class) {

                    @Override
                    protected void onComponentTag(ComponentTag tag) {
                        tag.put("type", "number");

                        super.onComponentTag(tag);
                    }
                };
            }
        };
        numberField.setLabel(label);
        numberField.add(RangeValidator.range(0, Integer.MAX_VALUE));
        return numberField;
    }

    protected IModel<String> amountNumberFieldLabelModel() {
        return new ResourceModel("amount", "Amount");
    }


    @Data
    public static class Bean implements Serializable {

        private String message;

        private Integer amount = 0;
    }
}
