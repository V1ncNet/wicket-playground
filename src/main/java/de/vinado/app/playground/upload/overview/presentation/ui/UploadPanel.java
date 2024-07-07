package de.vinado.app.playground.upload.overview.presentation.ui;

import de.vinado.app.playground.wicket.UpdateOnEventBehavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;

import java.util.List;
import java.util.stream.Stream;

public class UploadPanel extends GenericPanel<List<UploadableDocument>> {

    public UploadPanel(String id, IModel<List<UploadableDocument>> model) {
        super(id, model);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(documentOverview("documents"));
        add(uploadButton("upload"));
    }

    private WebMarkupContainer documentOverview(String wicketId) {
        IModel<Stream<UploadableDocument>> model = uploadOverviewModel();
        UploadOverview overview = new UploadOverview(wicketId, model);
        overview.add(new UpdateOnEventBehavior(UploadCompletedEvent.class));
        return overview;
    }

    private IModel<Stream<UploadableDocument>> uploadOverviewModel() {
        return getModel().map(List::stream);
    }

    private AbstractLink uploadButton(String wicketId) {
        IModel<UploadableDocuments> model = uploadButtonModel();
        return new UploadButton(wicketId, model);
    }

    private IModel<UploadableDocuments> uploadButtonModel() {
        return getModel().map(UploadableDocuments::new);
    }
}
