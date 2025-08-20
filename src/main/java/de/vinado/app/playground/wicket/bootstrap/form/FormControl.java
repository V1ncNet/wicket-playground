package de.vinado.app.playground.wicket.bootstrap.form;

import org.apache.wicket.model.IModel;

import java.io.Serializable;

public abstract class FormControl<T extends Serializable> extends BaseFormControl<T, T> {

    public FormControl(String id) {
        super(id);
    }

    public FormControl(String id, IModel<T> model) {
        super(id, model);
    }

    @Override
    public void convertInput() {
        setConvertedInput(getControl().getConvertedInput());
    }

    @Override
    protected void onBeforeRender() {
        super.onBeforeRender();

        getControl().setModelObject(getModelObject());
    }
}
