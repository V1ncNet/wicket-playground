package de.vinado.app.playground.wicket.bootstrap;

import de.vinado.app.playground.wicket.resource.CssResourceStreamAdapter;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.CssReferenceHeaderItem;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.request.resource.ResourceStreamResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class BootstrapCssResourceReference extends ResourceReference {

    private final Resource resource;

    private BootstrapCssResourceReference() {
        super("css/bootstrap.css");
        this.resource = new ClassPathResource(getName());
    }

    public static CssReferenceHeaderItem asHeaderItem() {
        return CssHeaderItem.forReference(Holder.INSTANCE);
    }

    @Override
    public IResource getResource() {
        return new ResourceStreamResource(new CssResourceStreamAdapter(resource));
    }


    private static class Holder {

        private static final BootstrapCssResourceReference INSTANCE = new BootstrapCssResourceReference();
    }
}
