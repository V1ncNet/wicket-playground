package de.vinado.app.playground.wicket.bootstrap.modal;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;

import lombok.Getter;

@Getter
public class FormModalPanel<T> extends GenericPanel<T> {

    private final Form<T> form;

    public FormModalPanel(String id) {
        this(id, null);
    }

    public FormModalPanel(String id, IModel<T> model) {
        super(id, model);

        this.form = form("form");
    }

    protected Form<T> form(String wicketId) {
        return new de.vinado.app.playground.wicket.bootstrap.form.Form<>(wicketId, getModel());
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(form);
    }

    @Override
    public FormModalPanel<T> add(Component... children) {
        for (Component child : children) {
            if (form == child) {
                super.add(children);
            } else {
                form.add(child);
            }
        }

        return this;
    }
}
