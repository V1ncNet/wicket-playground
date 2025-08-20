package de.vinado.app.playground.dashboard.presentation.ui;

import de.vinado.app.playground.wicket.PlaygroundPage;
import org.apache.wicket.markup.head.IHeaderResponse;

public class DashboardPage extends PlaygroundPage {

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);

        response.render(DashboardJavaScriptResourceReference.asHeaderItem());
    }
}
