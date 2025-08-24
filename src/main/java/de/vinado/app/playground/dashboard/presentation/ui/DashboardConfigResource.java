package de.vinado.app.playground.dashboard.presentation.ui;

import org.apache.wicket.request.resource.CharSequenceResource;

import java.util.Properties;
import java.util.function.BiConsumer;

public class DashboardConfigResource extends CharSequenceResource {

    private final Properties properties;

    public DashboardConfigResource(Properties properties) {
        super("application/javascript");
        this.properties = properties;
    }

    @Override
    protected CharSequence getData(Attributes attributes) {
        StringBuilder builder = new StringBuilder("window.__ENV__={");
        properties.forEach(appendTo(builder));
        builder.append("};");
        return builder.toString();
    }

    private static BiConsumer<Object, Object> appendTo(StringBuilder builder) {
        return (key, value) -> builder.append(key).append(":\"").append(value).append("\",");
    }
}
