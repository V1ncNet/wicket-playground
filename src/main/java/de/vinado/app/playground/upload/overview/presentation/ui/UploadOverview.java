package de.vinado.app.playground.upload.overview.presentation.ui;

import de.vinado.app.playground.wicket.bootstrap.icon.Bi;
import de.vinado.app.playground.wicket.image.Icon;
import de.vinado.app.playground.wicket.image.IconType;
import org.apache.wicket.Component;
import org.apache.wicket.IGenericComponent;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.markup.repeater.ReuseIfModelsEqualStrategy;
import org.apache.wicket.markup.repeater.util.ModelIteratorAdapter;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

import java.util.Iterator;
import java.util.stream.Stream;

public class UploadOverview extends GenericPanel<Stream<UploadableDocument>> {

    public UploadOverview(String id, IModel<Stream<UploadableDocument>> model) {
        super(id, model);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(documentEntries("documents"));
    }

    private Component documentEntries(String wicketId) {
        IModel<Stream<UploadableDocument>> model = documentEntriesModel();
        return new DocumentEntries(wicketId, model);
    }

    private IModel<Stream<UploadableDocument>> documentEntriesModel() {
        return getModel();
    }


    private static class DocumentEntries extends RefreshingView<UploadableDocument>
        implements IGenericComponent<Stream<UploadableDocument>, DocumentEntries> {

        public DocumentEntries(String id, IModel<Stream<UploadableDocument>> model) {
            super(id, model);

            setItemReuseStrategy(ReuseIfModelsEqualStrategy.getInstance());
        }

        @Override
        protected Iterator<IModel<UploadableDocument>> getItemModels() {
            return new ModelIteratorAdapter<>(getModelObject().iterator()) {

                @Override
                protected IModel<UploadableDocument> model(UploadableDocument document) {
                    return new CompoundPropertyModel<>(document);
                }
            };
        }

        @Override
        protected void populateItem(Item<UploadableDocument> item) {
            item.add(status("status", item));
            item.add(filename("filename", item));
        }

        private Component status(String wicketId, Item<UploadableDocument> item) {
            IModel<IconType> model = statusModel(item);
            return new Icon(wicketId, model);
        }

        private IModel<IconType> statusModel(Item<UploadableDocument> item) {
            return item.getModel().map(document -> document.isUploaded() ? Bi.CHECK : Bi.X);
        }

        private Component filename(String wicketId, Item<UploadableDocument> item) {
            IModel<?> model = filenameModel(item);
            return new Label(wicketId, model);
        }

        private IModel<?> filenameModel(Item<UploadableDocument> item) {
            return item.getModel().map(DocumentEntries::serialize);
        }

        private static String serialize(UploadableDocument document) {
            return document.getUri().toString();
        }
    }
}
