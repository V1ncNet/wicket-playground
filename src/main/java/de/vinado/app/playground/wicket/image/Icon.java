package de.vinado.app.playground.wicket.image;

import org.apache.wicket.IGenericComponent;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;

import static org.danekja.java.util.function.serializable.SerializableFunction.identity;

public class Icon extends WebMarkupContainer implements IGenericComponent<IconType, Icon> {

    public Icon(String id, IModel<? extends IconType> model) {
        super(id, model.map(identity()));
    }

    @Override
    protected void onComponentTag(ComponentTag tag) {
        super.onComponentTag(tag);

        tag.put("class", getModel().map(IconType::toCssClasses).getObject());
    }

    @Override
    protected void onConfigure() {
        super.onConfigure();

        setVisible(getModel().isPresent().getObject());
    }
}
