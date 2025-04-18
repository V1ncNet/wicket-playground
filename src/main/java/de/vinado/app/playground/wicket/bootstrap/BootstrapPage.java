package de.vinado.app.playground.wicket.bootstrap;

import de.vinado.app.playground.wicket.PlaygroundCssResourceReference;
import de.vinado.app.playground.wicket.bootstrap.modal.Modal;
import org.apache.wicket.Component;
import org.apache.wicket.Session;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.core.request.handler.IPageRequestHandler;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.filter.HeaderResponseContainer;
import org.apache.wicket.markup.html.TransparentWebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.cycle.RequestCycle;

import java.util.Locale;

import lombok.Getter;

import static de.vinado.app.playground.wicket.JavaScriptFilteredIntoFooterHeaderResponseDecorator.FILTER_NAME;

public abstract class BootstrapPage extends WebPage {

    @Getter
    private final Modal modal;

    public BootstrapPage() {
        this.modal = modal("modal");
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(html("html"));
        add(title("title"));
        add(homePageLink("navbarHomePageLink"));
        add(modal);
        add(footerBucket("footer-bucket"));
    }

    private TransparentWebMarkupContainer html(String wicketId) {
        return new HtmlTag(wicketId);
    }

    public static boolean isMounted() {
        return RequestCycle.get().find(IPageRequestHandler.class)
            .map(handler -> handler.getPage())
            .map(BootstrapPage.class::isInstance)
            .orElse(false);
    }

    public static BootstrapPage get() {
        return RequestCycle.get().find(IPageRequestHandler.class)
            .map(handler -> handler.getPage())
            .filter(BootstrapPage.class::isInstance)
            .map(BootstrapPage.class::cast)
            .orElseThrow(() -> new WicketRuntimeException("BootstrapPage is not mounted"));
    }

    private Modal modal(String wicketId) {
        return new Modal(wicketId);
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
