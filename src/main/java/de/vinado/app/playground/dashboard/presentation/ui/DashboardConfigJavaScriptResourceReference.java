package de.vinado.app.playground.dashboard.presentation.ui;

import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;

public class DashboardConfigJavaScriptResourceReference extends ResourceReference {

    private DashboardConfigJavaScriptResourceReference() {
        super(DashboardConfigJavaScriptResourceReference.class, "dashboard-config.js");
    }

    @Override
    public IResource getResource() {
        return new DashboardConfigResource();
    }

    public static JavaScriptReferenceHeaderItem asHeaderItem() {
        return JavaScriptHeaderItem.forReference(Holder.INSTANCE);
    }


    private static class Holder {

        private static final DashboardConfigJavaScriptResourceReference INSTANCE
            = new DashboardConfigJavaScriptResourceReference();
    }
}
