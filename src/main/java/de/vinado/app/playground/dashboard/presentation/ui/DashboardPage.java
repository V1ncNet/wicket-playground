package de.vinado.app.playground.dashboard.presentation.ui;

import de.vinado.app.playground.wicket.PlaygroundPage;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.head.IHeaderResponse;

@AuthorizeInstantiation({"USER"})
public class DashboardPage extends PlaygroundPage {

    public static final String PATH = "dashboard";

    @Override
    protected void onInitialize() {
        super.onInitialize();
        setVersioned(false);
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);

        response.render(DashboardJavaScriptResourceReference.asHeaderItem());
    }
}
