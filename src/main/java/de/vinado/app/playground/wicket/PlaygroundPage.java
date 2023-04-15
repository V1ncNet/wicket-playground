package de.vinado.app.playground.wicket;

import org.apache.wicket.Component;
import org.apache.wicket.Session;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.filter.HeaderResponseContainer;
import org.apache.wicket.markup.html.TransparentWebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;

import java.util.Locale;

import static de.vinado.app.playground.wicket.DefaultJavaScriptFilteredIntoFooterHeaderResponseDecorator.DEFAULT_FOOTER_FILTER_NAME;

public abstract class PlaygroundPage extends WebPage {

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(html("html"));
        add(footerBucket("footer-bucket"));
    }

    private TransparentWebMarkupContainer html(String wicketId) {
        return new HtmlTag(wicketId);
    }

    private Component footerBucket(String wicketId) {
        return new HeaderResponseContainer(wicketId, DEFAULT_FOOTER_FILTER_NAME);
    }


    private static class HtmlTag extends TransparentWebMarkupContainer {

        public HtmlTag(String id) {
            super(id);
        }

        @Override
        protected void onComponentTag(ComponentTag tag) {
            super.onComponentTag(tag);

            checkComponentTag(tag, "html");

            tag.put("lang", serialize(Session.get().getLocale()));
        }

        private String serialize(Locale locale) {
            return locale.toLanguageTag();
        }
    }
}
