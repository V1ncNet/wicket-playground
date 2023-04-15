package de.vinado.app.playground.wicket.navigation;

import de.vinado.app.playground.wicket.test.SpringEnabledWicketTestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

class SidebarTests extends SpringEnabledWicketTestCase {

    private Sidebar sidebar;

    @BeforeEach
    void setUp() {
        sidebar = new Sidebar("sidebar") {

            @Override
            protected Stream<NavigationItem> navigationEntries() {
                return Stream.empty();
            }
        };
    }

    @Test
    void startingSidebar_shouldRenderComponent() {
        tester.startComponentInPage(sidebar);

        tester.assertComponent("sidebar", Sidebar.class);
    }
}
