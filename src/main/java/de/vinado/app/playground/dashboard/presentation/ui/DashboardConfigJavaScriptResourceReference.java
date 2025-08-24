package de.vinado.app.playground.dashboard.presentation.ui;

import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;

import java.util.Properties;

public class DashboardConfigJavaScriptResourceReference extends ResourceReference {

    private final Properties properties;

    private DashboardConfigJavaScriptResourceReference(Properties properties) {
        super(DashboardConfigJavaScriptResourceReference.class, "dashboard-config.js");
        this.properties = properties;
    }

    @Override
    public IResource getResource() {
        return new DashboardConfigResource(properties);
    }

    public static JavaScriptReferenceHeaderItem asHeaderItem(Properties properties) {
        ResourceReference reference = new DashboardConfigJavaScriptResourceReference(properties);
        return JavaScriptHeaderItem.forReference(reference);
    }
}
