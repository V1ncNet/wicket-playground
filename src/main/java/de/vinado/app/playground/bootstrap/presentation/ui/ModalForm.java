package de.vinado.app.playground.bootstrap.presentation.ui;

import de.vinado.app.playground.wicket.bootstrap.form.FormLabel;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;

import java.io.Serializable;

import lombok.Data;

public class ModalForm extends GenericPanel<ModalForm.Bean> {

    private final Form<Bean> form;

    private FormComponent<String> messageTextField;

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
    }

    protected Form<Bean> form(String wicketId) {
        return new Form<>(wicketId, getModel());
    }

    protected FormComponent<String> messageTextField(String wicketId) {
        IModel<String> label = messageTextFieldLabelModel();
        TextField<String> textField = new TextField<>(wicketId);
        textField.setLabel(label);
        textField.setRequired(true);
        return textField;
    }

    protected IModel<String> messageTextFieldLabelModel() {
        return new ResourceModel("message", "Message");
    }

    protected WebMarkupContainer messageTextFieldLabel(String wicketId) {
        return new FormLabel(wicketId, messageTextField);
    }


    @Data
    public static class Bean implements Serializable {

        private String message;
    }
}
