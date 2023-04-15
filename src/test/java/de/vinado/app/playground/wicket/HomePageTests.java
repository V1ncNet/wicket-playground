package de.vinado.app.playground.wicket;

import de.vinado.app.playground.wicket.bootstrap.BootstrapBehavior;
import de.vinado.app.playground.wicket.navigation.Sidebar;
import de.vinado.app.playground.wicket.test.SpringEnabledWicketTestCase;
import org.apache.wicket.markup.html.TransparentWebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.junit.jupiter.api.Test;

class HomePageTests extends SpringEnabledWicketTestCase {

    @Test
    void callingUrl_shouldRenderPage() {
        tester.executeUrl("/");

        tester.assertRenderedPage(HomePage.class);
    }

    @Test
    void renderingPage_shouldRenderPage() {
        tester.startPage(HomePage.class);

        tester.assertRenderedPage(HomePage.class);
    }

    @Test
    void renderingPage_shouldApplyBootstrap() {
        tester.startPage(HomePage.class);

        tester.assertBehavior("", BootstrapBehavior.class);
    }

    @Test
    void renderingPage_shouldConfigureHtmlElement() {
        tester.startPage(HomePage.class);

        tester.assertComponent("html", TransparentWebMarkupContainer.class);
    }

    @Test
    void renderingPage_shouldRenderTitle() {
        tester.startPage(HomePage.class);

        tester.assertComponent("title", Label.class);
    }

    @Test
    void renderingPage_shouldRenderNavigation() {
        tester.startPage(HomePage.class);

        tester.assertComponent("navigation", Sidebar.class);
    }
}
