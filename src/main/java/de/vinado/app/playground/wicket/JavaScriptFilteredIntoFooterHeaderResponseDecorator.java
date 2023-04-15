package de.vinado.app.playground.wicket;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.filter.JavaScriptFilteredIntoFooterHeaderResponse;
import org.apache.wicket.markup.html.IHeaderResponseDecorator;

public class JavaScriptFilteredIntoFooterHeaderResponseDecorator implements IHeaderResponseDecorator {

    public static final String FILTER_NAME = "footer-bucket";

    @Override
    public IHeaderResponse decorate(IHeaderResponse response) {
        return new JavaScriptFilteredIntoFooterHeaderResponse(response, FILTER_NAME);
    }
}
