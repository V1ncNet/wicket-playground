package de.vinado.app.playground.wicket.codimd;

import org.apache.wicket.IGenericComponent;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.Objects;
import java.util.Optional;

import static de.vinado.function.SerializableThrowingFunction.sneaky;

public class CodiMd extends GenericPanel<Note> {

    @SpringBean
    private CodiMdUrlProvider urlProvider;

    public CodiMd(String id, IModel<Note> model) {
        super(id, model);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        setOutputMarkupPlaceholderTag(true);

        add(pad("pad"));
    }

    private Iframe pad(String wicketId) {
        return new Iframe(wicketId, getModel());
    }

    @Override
    protected void onConfigure() {
        super.onConfigure();

        setVisible(hasModelObject());
    }

    private Boolean hasModelObject() {
        return Optional.ofNullable(getModel())
            .map(IModel::isPresent)
            .map(IModel::getObject)
            .orElse(false);
    }


    private class Iframe extends WebMarkupContainer implements IGenericComponent<Note, Iframe> {

        public Iframe(String id, IModel<Note> model) {
            super(id, model);
        }

        @Override
        protected void onInitialize() {
            super.onInitialize();

            setOutputMarkupId(true);
        }

        @Override
        protected void onComponentTag(ComponentTag tag) {
            super.onComponentTag(tag);

            IModel<String> model = getModel()
                .map(sneaky(urlProvider::resolve))
                .map(Objects::toString);
            tag.put("src", model.getObject());
        }
    }
}
