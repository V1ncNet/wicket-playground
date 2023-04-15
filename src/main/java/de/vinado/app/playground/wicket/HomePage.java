package de.vinado.app.playground.wicket;

import de.vinado.app.playground.wicket.bootstrap.BootstrapCssResourceReference;
import de.vinado.app.playground.wicket.bootstrap.BootstrapJavaScriptResourceReference;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;

public class HomePage extends WebPage {

    @Override
    public void renderHead(IHeaderResponse response) {
        response.render(BootstrapCssResourceReference.asHeaderItem());
        response.render(BootstrapJavaScriptResourceReference.asHeaderItem());
    }
}
