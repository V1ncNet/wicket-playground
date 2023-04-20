package de.vinado.app.playground.wicket;

import de.vinado.app.playground.wicket.bootstrap.BootstrapBehavior;
import de.vinado.app.playground.wicket.navigation.Sidebar;
import de.vinado.app.playground.wicket.test.SpringEnabledWicketTestCase;
import org.apache.wicket.markup.html.TransparentWebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = EmptyPageConfiguration.class)
class EmptyPageTests extends SpringEnabledWicketTestCase {

    @Test
    void callingUrl_shouldRenderPage() {
        tester.executeUrl("empty");

        tester.assertRenderedPage(EmptyPage.class);
    }

    @Test
    void renderingPage_shouldRenderPage() {
        tester.startPage(EmptyPage.class);

        tester.assertRenderedPage(EmptyPage.class);
    }

    @Test
    void renderingPage_shouldApplyBootstrap() {
        tester.startPage(EmptyPage.class);

        tester.assertBehavior("", BootstrapBehavior.class);
    }

    @Test
    void renderingPage_shouldConfigureHtmlElement() {
        tester.startPage(EmptyPage.class);

        tester.assertComponent("html", TransparentWebMarkupContainer.class);
    }

    @Test
    void renderingPage_shouldRenderTitle() {
        tester.startPage(EmptyPage.class);

        tester.assertComponent("title", Label.class);
    }

    @Test
    void renderingPage_shouldRenderNavigation() {
        tester.startPage(EmptyPage.class);

        tester.assertComponent("navigation", Sidebar.class);
    }
}
