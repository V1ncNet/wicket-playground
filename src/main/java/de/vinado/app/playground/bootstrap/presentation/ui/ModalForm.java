package de.vinado.app.playground.bootstrap.presentation.ui;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;

import java.io.Serializable;

import lombok.Data;

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
    }

    protected Form<Bean> form(String wicketId) {
        return new Form<>(wicketId, getModel());
    }

    @Data
    public static class Bean implements Serializable {
    }
}
