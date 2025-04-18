package de.vinado.app.playground.bootstrap.presentation.ui.modal;

import de.vinado.app.playground.wicket.bootstrap.form.FormControl;
import de.vinado.app.playground.wicket.bootstrap.modal.FormModalPanel;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.validation.validator.RangeValidator;
import org.apache.wicket.validation.validator.StringValidator;

public class DemoModalDialogForm extends FormModalPanel<DemoDto> {

    public DemoModalDialogForm(String id, IModel<DemoDto> model) {
        super(id, model);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(messageInput("message"));
        add(amountInput("amount"));
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
}
