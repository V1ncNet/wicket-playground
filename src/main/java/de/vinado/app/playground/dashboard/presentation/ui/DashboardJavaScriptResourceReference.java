package de.vinado.app.playground.dashboard.presentation.ui;

import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.markup.head.JavaScriptReferenceType;
import org.apache.wicket.request.resource.PackageResource;
import org.apache.wicket.request.resource.PackageResourceReference;

public class DashboardJavaScriptResourceReference extends PackageResourceReference {

    public DashboardJavaScriptResourceReference() {
        super(DashboardJavaScriptResourceReference.class, "dashboard.js");
    }

    @Override
    public PackageResource getResource() {
        PackageResource resource = super.getResource();
        resource.setCachingEnabled(false);
        return resource;
    }

    public static JavaScriptReferenceHeaderItem asHeaderItem() {
        JavaScriptReferenceHeaderItem item = new JavaScriptReferenceHeaderItem(Holder.INSTANCE, null, null);
        item.setType(JavaScriptReferenceType.MODULE);
        return item;
    }


    private static class Holder {

        private static final DashboardJavaScriptResourceReference INSTANCE = new DashboardJavaScriptResourceReference();
    }
}
