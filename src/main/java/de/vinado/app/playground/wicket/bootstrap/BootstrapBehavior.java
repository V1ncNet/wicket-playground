package de.vinado.app.playground.wicket.bootstrap;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;

public class BootstrapBehavior extends Behavior {

    private BootstrapBehavior() {
    }

    public static BootstrapBehavior getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public void renderHead(Component component, IHeaderResponse response) {
        response.render(BootstrapCssResourceReference.asHeaderItem());
        response.render(BootstrapJavaScriptResourceReference.asHeaderItem());
    }


    private static class Holder {

        private static final BootstrapBehavior INSTANCE = new BootstrapBehavior();
    }
}
