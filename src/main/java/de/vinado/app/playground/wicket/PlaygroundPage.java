package de.vinado.app.playground.wicket;

import org.apache.wicket.Component;
import org.apache.wicket.Session;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.filter.HeaderResponseContainer;
import org.apache.wicket.markup.html.TransparentWebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;

import java.util.Locale;

import static de.vinado.app.playground.wicket.JavaScriptFilteredIntoFooterHeaderResponseDecorator.FILTER_NAME;

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
        return new HeaderResponseContainer(wicketId, FILTER_NAME);
    }

    @Override
    protected void onBeforeRender() {
        add(title("title"));

        super.onBeforeRender();
    }

    private Label title(String wicketId) {
        return new Label(wicketId, titleModel());
    }

    protected IModel<?> titleModel() {
        return () -> "Wicket Playground";
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
