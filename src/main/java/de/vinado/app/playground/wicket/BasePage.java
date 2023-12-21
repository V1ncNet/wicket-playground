package de.vinado.app.playground.wicket;

import lombok.AccessLevel;
import lombok.Getter;
import org.apache.wicket.Component;
import org.apache.wicket.Session;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.filter.HeaderResponseContainer;
import org.apache.wicket.markup.html.TransparentWebMarkupContainer;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;

import java.util.Locale;

import static de.vinado.app.playground.wicket.JavaScriptFilteredIntoFooterHeaderResponseDecorator.FILTER_NAME;

public abstract class BasePage extends WebPage {

    @Getter(AccessLevel.PROTECTED)
    private final WebMarkupContainer content;

    public BasePage() {
        this.content = content("content");
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(html("html"));
        add(title("title"));
        add(content("content"));
        add(footerBucket("footer-bucket"));
    }

    protected TransparentWebMarkupContainer html(String wicketId) {
        return new HtmlTag(wicketId);
    }

    protected Component title(String wicketId) {
        return new Label(wicketId, titleModel());
    }

    protected IModel<?> titleModel() {
        return () -> "Wicket Playground";
    }

    protected WebMarkupContainer content(String wicketId) {
        TransparentWebMarkupContainer container = new TransparentWebMarkupContainer(wicketId);
        container.setRenderBodyOnly(true);
        return container;
    }

    protected Component footerBucket(String wicketId) {
        return new HeaderResponseContainer(wicketId, FILTER_NAME);
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
