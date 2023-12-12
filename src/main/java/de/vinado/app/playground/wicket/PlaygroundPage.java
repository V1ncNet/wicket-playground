package de.vinado.app.playground.wicket;

import de.vinado.app.playground.wicket.navigation.NavigationItem;
import de.vinado.app.playground.wicket.navigation.NavigationItemSupplier;
import de.vinado.app.playground.wicket.navigation.Sidebar;
import org.apache.wicket.Component;
import org.apache.wicket.Session;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.filter.HeaderResponseContainer;
import org.apache.wicket.markup.html.TransparentWebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.Locale;
import java.util.stream.Stream;

import static de.vinado.app.playground.wicket.JavaScriptFilteredIntoFooterHeaderResponseDecorator.FILTER_NAME;

public abstract class PlaygroundPage extends WebPage {

    @SpringBean
    private NavigationItemSupplier supplier;

    private Sidebar content;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(html("html"));
        add(title("title"));
        add(content = navigation("navigation"));
        add(footerBucket("footer-bucket"));
    }

    private TransparentWebMarkupContainer html(String wicketId) {
        return new HtmlTag(wicketId);
    }

    private Sidebar navigation(String wicketId) {
        IModel<Stream<NavigationItem>> model = supplier::get;
        return new Sidebar(wicketId, model);
    }

    private Component footerBucket(String wicketId) {
        return new HeaderResponseContainer(wicketId, FILTER_NAME);
    }

    private Label title(String wicketId) {
        return new Label(wicketId, titleModel());
    }

    protected IModel<?> titleModel() {
        return () -> "Wicket Playground";
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        response.render(PlaygroundCssResourceReference.asHeaderItem());
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
