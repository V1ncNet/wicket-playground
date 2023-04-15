package de.vinado.app.playground.wicket.navigation;

import de.vinado.app.playground.wicket.bootstrap.BootstrapCssResourceReference;
import org.apache.wicket.markup.head.CssReferenceHeaderItem;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.request.resource.CssResourceReference;

import java.util.List;

class NavigationCssResourceReference extends CssResourceReference {

    private NavigationCssResourceReference() {
        super(NavigationCssResourceReference.class, "navigation.css");
    }

    public static CssReferenceHeaderItem asHeaderItem() {
        return CssReferenceHeaderItem.forReference(Holder.INSTANCE);
    }

    @Override
    public List<HeaderItem> getDependencies() {
        List<HeaderItem> dependencies = super.getDependencies();
        dependencies.add(BootstrapCssResourceReference.asHeaderItem());
        return dependencies;
    }

    private static class Holder {

        private static final NavigationCssResourceReference INSTANCE = new NavigationCssResourceReference();
    }
}
