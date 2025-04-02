package de.vinado.app.playground.wicket;

import de.vinado.app.playground.wicket.bootstrap.BootstrapPage;
import de.vinado.app.playground.wicket.navigation.NavigationItem;
import de.vinado.app.playground.wicket.navigation.NavigationItemSupplier;
import de.vinado.app.playground.wicket.navigation.Sidebar;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.stream.Stream;

import lombok.AccessLevel;
import lombok.Getter;

public abstract class PlaygroundPage extends BootstrapPage {

    @SpringBean
    private NavigationItemSupplier supplier;

    @Getter(AccessLevel.PROTECTED)
    private final WebMarkupContainer content;

    public PlaygroundPage() {
        this.content = navigation("navigation");
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(content);
    }

    private Sidebar navigation(String wicketId) {
        IModel<Stream<NavigationItem>> model = supplier::get;
        return new Sidebar(wicketId, model);
    }
}
