package de.vinado.app.playground.wicket;

import de.vinado.app.playground.document.presentation.ui.PreviewPage;
import de.vinado.app.playground.wicket.bootstrap.icon.Bi;
import de.vinado.app.playground.wicket.navigation.NavigationItem;
import de.vinado.app.playground.wicket.navigation.Sidebar;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.Session;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.IHeaderResponse;
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
        NavigationItem.builder(PreviewPage.class, "Document Preview").icon(Bi.FILETYPE_PDF).build(),
        NavigationItem.builder(EmptyPage.class, "Empty").icon(Bi.FILE).build(),
    };

    private Sidebar content;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        super.add(html("html"));
        super.add(content = navigation("navigation"));
        super.add(footerBucket("footer-bucket"));
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
        super.add(title("title"));

        super.onBeforeRender();
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

    @Override
    public MarkupContainer add(Component... children) {
        return content.add(children);
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
