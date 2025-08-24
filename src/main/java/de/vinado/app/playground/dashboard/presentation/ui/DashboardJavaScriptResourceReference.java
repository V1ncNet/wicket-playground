package de.vinado.app.playground.dashboard.presentation.ui;

import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.markup.head.JavaScriptReferenceType;
import org.apache.wicket.request.resource.PackageResource;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.request.resource.ResourceReference;

import java.util.List;
import java.util.Properties;

public class DashboardJavaScriptResourceReference extends PackageResourceReference {

    private final Properties properties;

    private DashboardJavaScriptResourceReference(Properties properties) {
        super(DashboardJavaScriptResourceReference.class, "dashboard.js");
        this.properties = properties;
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
        dependencies.add(DashboardConfigJavaScriptResourceReference.asHeaderItem(properties));
        return dependencies;
    }

    public static JavaScriptReferenceHeaderItem asHeaderItem(Properties properties) {
        ResourceReference reference = new DashboardJavaScriptResourceReference(properties);
        JavaScriptReferenceHeaderItem item = new JavaScriptReferenceHeaderItem(reference, null, null);
        item.setType(JavaScriptReferenceType.MODULE);
        return item;
    }
}
