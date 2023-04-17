package de.vinado.app.playground.document.presentation.ui;

import de.vinado.app.playground.wicket.PlaygroundPage;
import org.apache.wicket.model.IModel;

import java.net.URI;

public class PreviewPage extends PlaygroundPage {

    private static final URI DUMMY_PDF = URI.create("https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf");

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(preview("preview"));
    }

    private MediaViewer preview(String wicketId) {
        IModel<ViewableDocument> model = () -> new ViewableDocument(DUMMY_PDF);
        return new MediaViewer(wicketId, model);
    }
}
