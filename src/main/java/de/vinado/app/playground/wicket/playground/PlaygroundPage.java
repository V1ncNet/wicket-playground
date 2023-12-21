package de.vinado.app.playground.wicket.playground;

import de.vinado.app.playground.wicket.BasePage;
import de.vinado.app.playground.wicket.playground.navigation.NavigationItem;
import de.vinado.app.playground.wicket.playground.navigation.NavigationItemSupplier;
import de.vinado.app.playground.wicket.playground.navigation.Sidebar;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.stream.Stream;

public abstract class PlaygroundPage extends BasePage {

    @SpringBean
    private NavigationItemSupplier supplier;

    @Override
    protected WebMarkupContainer content(String wicketId) {
        IModel<Stream<NavigationItem>> model = supplier::get;
        return new Sidebar(wicketId, model);
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        response.render(PlaygroundCssResourceReference.asHeaderItem());
    }
}
