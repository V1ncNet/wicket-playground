package de.vinado.app.playground.wicket;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.filter.JavaScriptFilteredIntoFooterHeaderResponse;
import org.apache.wicket.markup.html.IHeaderResponseDecorator;

public class DefaultJavaScriptFilteredIntoFooterHeaderResponseDecorator implements IHeaderResponseDecorator {

    public static final String DEFAULT_FOOTER_FILTER_NAME = "wicket-default-footer-filter";

    @Override
    public IHeaderResponse decorate(IHeaderResponse response) {
        return new JavaScriptFilteredIntoFooterHeaderResponse(response, DEFAULT_FOOTER_FILTER_NAME);
    }
}
