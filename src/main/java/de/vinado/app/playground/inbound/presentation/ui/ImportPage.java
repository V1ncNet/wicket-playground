package de.vinado.app.playground.inbound.presentation.ui;

import de.vinado.app.playground.wicket.PlaygroundPage;
import org.apache.wicket.model.ComponentPropertyModel;
import org.apache.wicket.model.CompoundPropertyModel;

public class ImportPage extends PlaygroundPage {

    @Override
    protected void onInitialize() {
        super.onInitialize();

        queue(new ImportForm("form", new CompoundPropertyModel<>(new ImportForm.Data())));
        queue(new ProgressPanel("progress", new CompoundPropertyModel<>(new ImportProgress())));
    }
}
