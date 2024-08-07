package de.vinado.app.playground.document.presentation.ui;

import org.apache.wicket.IGenericComponent;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.Objects;
import java.util.Optional;

import static de.vinado.function.SerializableThrowingFunction.sneaky;

public class MediaViewer extends GenericPanel<ViewableDocument> {

    @SpringBean
    private PreviewUrlProvider previewUrlProvider;

    public MediaViewer(String id) {
        super(id);
    }

    public MediaViewer(String id, IModel<ViewableDocument> model) {
        super(id, model);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        setOutputMarkupPlaceholderTag(true);

        add(iframe("viewer"));
    }

    private Iframe iframe(String wicketId) {
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


    private class Iframe extends WebMarkupContainer implements IGenericComponent<ViewableDocument, Iframe> {

        public Iframe(String id, IModel<ViewableDocument> model) {
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
                .map(sneaky(previewUrlProvider::resolve))
                .map(Objects::toString);
            tag.put("src", model.getObject());
        }
    }
}
