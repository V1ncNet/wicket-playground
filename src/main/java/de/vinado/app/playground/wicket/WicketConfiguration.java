package de.vinado.app.playground.wicket;

import org.apache.wicket.protocol.http.WicketFilter;
import org.apache.wicket.spring.SpringWebApplicationFactory;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;

import java.util.EnumSet;
import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;

import static javax.servlet.DispatcherType.*;
import static org.apache.wicket.protocol.http.WicketFilter.*;

@Configuration
public class WicketConfiguration implements ServletContextInitializer {

    public static final String APP_ROOT = "/*";

    public static final EnumSet<DispatcherType> DISPATCHER_TYPES = EnumSet.of(REQUEST, ERROR, FORWARD, ASYNC);

    @Override
    public void onStartup(ServletContext servletContext) {
        FilterRegistration filter = servletContext.addFilter("wicket.playground", WicketFilter.class);
        filter.setInitParameter(APP_FACT_PARAM, SpringWebApplicationFactory.class.getName());
        filter.setInitParameter(IGNORE_PATHS_PARAM, "/static");
        filter.setInitParameter(FILTER_MAPPING_PARAM, APP_ROOT);
        filter.addMappingForUrlPatterns(DISPATCHER_TYPES, false, APP_ROOT);
    }
}
