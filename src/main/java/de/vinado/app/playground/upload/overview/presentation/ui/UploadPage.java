package de.vinado.app.playground.upload.overview.presentation.ui;

import de.vinado.app.playground.wicket.PlaygroundPage;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.util.ListModel;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static de.vinado.app.playground.upload.overview.presentation.ui.UploadableDocument.fromPath;

public class UploadPage extends PlaygroundPage {

    @Override
    protected void onInitialize() {
        super.onInitialize();

        queue(uploadPanel("upload"));
    }

    private WebMarkupContainer uploadPanel(String wicketId) {
        IModel<List<UploadableDocument>> model = uploadPanelModel();
        return new UploadPanel(wicketId, model);
    }

    private IModel<List<UploadableDocument>> uploadPanelModel() {
        List<UploadableDocument> documents = IntStream.range(1, 21)
            .mapToObj(i -> fromPath(String.format("/Users/vincent/file%02d.pdf", i)))
            .collect(Collectors.toList());
        return new ListModel<>(documents);
    }
}
