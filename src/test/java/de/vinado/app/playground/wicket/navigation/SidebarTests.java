package de.vinado.app.playground.wicket.navigation;

import de.vinado.app.playground.wicket.test.SpringEnabledWicketTestCase;
import org.junit.jupiter.api.Test;

class SidebarTests extends SpringEnabledWicketTestCase {

    @Test
    void startingSidebar_shouldRenderComponent() {
        tester.startComponentInPage(Sidebar.class);

        tester.assertComponent("", Sidebar.class);
    }
}
