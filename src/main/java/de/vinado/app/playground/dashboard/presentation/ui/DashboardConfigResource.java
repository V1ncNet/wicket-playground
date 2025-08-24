package de.vinado.app.playground.dashboard.presentation.ui;

import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.resource.CharSequenceResource;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Properties;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class DashboardConfigResource extends CharSequenceResource {

    private final RequestHolder request;

    public DashboardConfigResource() {
        this(() -> (HttpServletRequest) RequestCycle.get().getRequest().getContainerRequest());
    }

    public DashboardConfigResource(RequestHolder request) {
        super("application/javascript");
        this.request = request;
    }

    @Override
    protected CharSequence getData(Attributes attributes) {
        Properties properties = new Properties();

        HttpServletRequest request = this.request.get();
        UriComponents uriComponents = ServletUriComponentsBuilder.fromContextPath(request).build();
        properties.put("BASE_PATH", uriComponents.getPath() + DashboardPage.PATH);

        StringBuilder builder = new StringBuilder("window.__ENV__={");
        properties.forEach(appendTo(builder));
        builder.append("};");
        return builder.toString();
    }

    private static <K, V> BiConsumer<K, V> appendTo(StringBuilder builder) {
        return (key, value) -> builder.append(key).append(":\"").append(value).append("\",");
    }


    @FunctionalInterface
    public interface RequestHolder extends Supplier<HttpServletRequest> {
    }
}
