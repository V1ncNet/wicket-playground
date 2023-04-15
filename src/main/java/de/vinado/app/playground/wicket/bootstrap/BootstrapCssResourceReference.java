package de.vinado.app.playground.wicket.bootstrap;

import de.agilecoders.wicket.webjars.request.resource.WebjarsCssResourceReference;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.CssReferenceHeaderItem;

public class BootstrapCssResourceReference extends WebjarsCssResourceReference {

    private BootstrapCssResourceReference() {
        super("bootstrap/current/css/bootstrap.css");
    }

    public static CssReferenceHeaderItem asHeaderItem() {
        return CssHeaderItem.forReference(Holder.INSTANCE);
    }


    private static class Holder {

        private static final BootstrapCssResourceReference INSTANCE = new BootstrapCssResourceReference();
    }
}
