package de.vinado.app.playground.wicket.navigation;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.border.Border;

public class Sidebar extends Border {

    public Sidebar(String id) {
        super(id);
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        response.render(NavigationCssResourceReference.asHeaderItem());
    }
}
