package de.vinado.app.playground.wicket.bootstrap.form;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.util.visit.IVisitor;

public class Form<T> extends org.apache.wicket.markup.html.form.Form<T> {

    public Form(String id) {
        super(id);
    }

    public Form(String id, IModel<T> model) {
        super(id, model);
    }

    @Override
    protected void onValidate() {
        RequestCycle.get().find(AjaxRequestTarget.class).ifPresent(target -> {
            IVisitor<FormComponent<?>, Void> visitor = visitor(target);
            visitFormComponents(visitor);
        });
    }

    protected IVisitor<FormComponent<?>, Void> visitor(AjaxRequestTarget target) {
        return (component, visit) -> {
            if (component.getOutputMarkupId() && (component.isRequired() || !component.getValidators().isEmpty())) {
                target.add(component);
            }
        };
    }
}
