package de.vinado.app.playground.wicket.bootstrap.form;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.LabeledWebMarkupContainer;

public class FormLabel extends WebMarkupContainer {

    private final FormComponent<?> formComponent;

    public FormLabel(String id, FormComponent<?> formComponent) {
        super(id);

        assertLabel(formComponent, "Provided form component does not have a label set.");

        this.formComponent = formComponent;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        formComponent.setOutputMarkupId(true);
        setDefaultModel(formComponent.getLabel());

        add(AttributeModifier.replace("class", "form-label"));
    }

    @Override
    protected void onComponentTag(ComponentTag tag) {
        super.onComponentTag(tag);

        checkComponentTag(tag, "label");

        tag.put("for", formComponent.getMarkupId());
    }

    @Override
    public void onComponentTagBody(MarkupStream markupStream, ComponentTag openTag) {
        String label = getDefaultModelObjectAsString();

        if (formComponent.isRequired()) {
            label += " *";
        }

        replaceComponentTagBody(markupStream, openTag, label);
    }

    private static void assertLabel(LabeledWebMarkupContainer provider, String message) {
        if (null == provider.getLabel()) {
            throw new IllegalStateException(message);
        }
    }
}
