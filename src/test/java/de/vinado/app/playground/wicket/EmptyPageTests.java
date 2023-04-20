package de.vinado.app.playground.wicket;

import de.vinado.app.playground.wicket.bootstrap.BootstrapBehavior;
import de.vinado.app.playground.wicket.navigation.NavigationItemSupplier;
import de.vinado.app.playground.wicket.navigation.Sidebar;
import de.vinado.app.playground.wicket.test.SpringEnabledWicketTestCase;
import org.apache.wicket.markup.html.TransparentWebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.util.stream.Stream;

import static org.mockito.Mockito.*;

@ContextConfiguration(classes = EmptyPageConfiguration.class)
class EmptyPageTests extends SpringEnabledWicketTestCase {

    @MockBean
    private NavigationItemSupplier supplier;

    @BeforeEach
    void setUp() {
        when(supplier.get()).thenAnswer(i -> Stream.empty());
    }

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
