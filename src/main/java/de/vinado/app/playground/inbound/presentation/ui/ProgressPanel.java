package de.vinado.app.playground.inbound.presentation.ui;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;

public class ProgressPanel extends GenericPanel<ImportProgress> {

    public ProgressPanel(String id, IModel<ImportProgress> model) {
        super(id, model);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        setOutputMarkupId(true);

        IModel<String> messageModel = getModel().map(ImportProgress::message);
        add(new Label("message", messageModel));

        IModel<String> valueModel = getModel().map(this::percentage);
        add(new Label("value", valueModel));
    }

    private String percentage(ImportProgress importProgress) {
        int value = importProgress.value();
        return value + "%";
    }
}
