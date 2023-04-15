package de.vinado.app.playground.wicket;

import lombok.RequiredArgsConstructor;
import org.apache.wicket.protocol.http.WicketFilter;
import org.apache.wicket.spring.SpringWebApplicationFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;

import java.util.EnumSet;
import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;

import static javax.servlet.DispatcherType.*;
import static org.apache.wicket.protocol.http.WicketFilter.*;

@Configuration
@EnableConfigurationProperties(WicketProperties.class)
@RequiredArgsConstructor
public class WicketConfiguration implements ServletContextInitializer {

    public static final String APP_ROOT = "/*";

    private static final String RUNTIME_CONFIGURATION_PARAM = "configuration";
    public static final EnumSet<DispatcherType> DISPATCHER_TYPES = EnumSet.of(REQUEST, ERROR, FORWARD, ASYNC);

    private final WicketProperties properties;

    @Override
    public void onStartup(ServletContext servletContext) {
        FilterRegistration filter = servletContext.addFilter("wicket.playground", WicketFilter.class);
        filter.setInitParameter(APP_FACT_PARAM, SpringWebApplicationFactory.class.getName());
        filter.setInitParameter(IGNORE_PATHS_PARAM, "/static");
        filter.setInitParameter(FILTER_MAPPING_PARAM, APP_ROOT);
        filter.setInitParameter(RUNTIME_CONFIGURATION_PARAM, properties.getRuntimeConfiguration().name());
        filter.addMappingForUrlPatterns(DISPATCHER_TYPES, false, APP_ROOT);
    }
}
