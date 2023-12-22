package de.vinado.app.playground.wicket.bootstrap.home;

import de.vinado.app.playground.wicket.BasePage;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

public class HomePage extends BasePage {

    @Override
    protected void onInitialize() {
        super.onInitialize();

        queue(form("form"));
    }

    protected TestForm form(String wicketId) {
        IModel<Bean> model = new CompoundPropertyModel<>(new Bean());
        return new TestForm(wicketId, model);
    }
}
