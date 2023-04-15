package de.vinado.app.playground.wicket;

import de.vinado.app.playground.wicket.bootstrap.BootstrapBehavior;
import org.apache.wicket.markup.html.WebPage;

public class HomePage extends WebPage {

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(BootstrapBehavior.getInstance());
    }
}
