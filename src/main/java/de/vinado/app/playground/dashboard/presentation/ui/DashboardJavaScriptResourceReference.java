package de.vinado.app.playground.dashboard.presentation.ui;

import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.markup.head.JavaScriptReferenceType;
import org.apache.wicket.request.resource.PackageResource;
import org.apache.wicket.request.resource.PackageResourceReference;

import java.util.List;

public class DashboardJavaScriptResourceReference extends PackageResourceReference {

    private DashboardJavaScriptResourceReference() {
        super(DashboardJavaScriptResourceReference.class, "dashboard.js");
    }

    @Override
    public PackageResource getResource() {
        PackageResource resource = super.getResource();
        resource.setCachingEnabled(false);
        return resource;
    }

    @Override
    public List<HeaderItem> getDependencies() {
        List<HeaderItem> dependencies = super.getDependencies();
        dependencies.add(DashboardConfigJavaScriptResourceReference.asHeaderItem());
        return dependencies;
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
