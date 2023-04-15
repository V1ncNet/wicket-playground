package de.vinado.app.playground.wicket;

import de.vinado.app.playground.wicket.bootstrap.icon.Bi;
import de.vinado.app.playground.wicket.empty.EmptyPage;
import de.vinado.app.playground.wicket.navigation.NavigationItem;
import de.vinado.app.playground.wicket.navigation.Sidebar;
import org.apache.wicket.Component;
import org.apache.wicket.Session;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.filter.HeaderResponseContainer;
import org.apache.wicket.markup.html.TransparentWebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;

import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Stream;

import static de.vinado.app.playground.wicket.JavaScriptFilteredIntoFooterHeaderResponseDecorator.FILTER_NAME;

public abstract class PlaygroundPage extends WebPage {

    public static final NavigationItem[] ITEMS = {
        NavigationItem.builder(HomePage.class, "Home").icon(Bi.HOUSE).build(),
        NavigationItem.builder(EmptyPage.class, "Empty").icon(Bi.FILE).build(),
    };

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(AttributeAppender.append("class", "container-fluid"));

        add(html("html"));
        add(navigation("navigation"));
        add(footerBucket("footer-bucket"));
    }

    private TransparentWebMarkupContainer html(String wicketId) {
        return new HtmlTag(wicketId);
    }

    private Sidebar navigation(String wicketId) {
        IModel<Stream<NavigationItem>> model = () -> Arrays.stream(ITEMS);
        return new Sidebar(wicketId, model);
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
