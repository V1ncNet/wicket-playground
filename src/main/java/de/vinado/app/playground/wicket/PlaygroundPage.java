package de.vinado.app.playground.wicket;

import org.apache.wicket.Component;
import org.apache.wicket.markup.head.filter.HeaderResponseContainer;
import org.apache.wicket.markup.html.WebPage;

import static de.vinado.app.playground.wicket.DefaultJavaScriptFilteredIntoFooterHeaderResponseDecorator.DEFAULT_FOOTER_FILTER_NAME;

public abstract class PlaygroundPage extends WebPage {

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(footerBucket("footer-bucket"));
    }

    private Component footerBucket(String wicketId) {
        return new HeaderResponseContainer(wicketId, DEFAULT_FOOTER_FILTER_NAME);
    }
}
