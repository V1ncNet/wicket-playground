package de.vinado.app.playground.wicket;

import org.apache.wicket.protocol.http.WicketFilter;
import org.apache.wicket.spring.SpringWebApplicationFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import jakarta.servlet.DispatcherType;
import java.util.EnumSet;

import lombok.RequiredArgsConstructor;

import static jakarta.servlet.DispatcherType.REQUEST;
import static org.apache.wicket.protocol.http.WicketFilter.APP_FACT_PARAM;
import static org.apache.wicket.protocol.http.WicketFilter.FILTER_MAPPING_PARAM;
import static org.apache.wicket.protocol.http.WicketFilter.IGNORE_PATHS_PARAM;

@Profile("wicket")
@Configuration
@EnableConfigurationProperties(WicketProperties.class)
@RequiredArgsConstructor
public class WicketConfiguration {

    public static final String APP_ROOT = "/*";

    private static final String RUNTIME_CONFIGURATION_PARAM = "configuration";
    private static final EnumSet<DispatcherType> DISPATCHER_TYPES = EnumSet.of(REQUEST);
    private static final String[] IGNORED_PATHS = {
        "/favicon.ico",
        "/robots.txt",
        "/static",
        "/api/*",
    };

    private final WicketProperties properties;

    @Bean
    public FilterRegistrationBean<WicketFilter> wicketFilter() {
        WicketFilter filter = new WicketFilter();
        FilterRegistrationBean<WicketFilter> registration = new FilterRegistrationBean<>(filter);
        registration.setName("wicket.playground");
        registration.addInitParameter(APP_FACT_PARAM, SpringWebApplicationFactory.class.getName());
        registration.addInitParameter(IGNORE_PATHS_PARAM, String.join(",", IGNORED_PATHS));
        registration.addInitParameter(FILTER_MAPPING_PARAM, APP_ROOT);
        registration.addInitParameter(RUNTIME_CONFIGURATION_PARAM, properties.getRuntimeConfiguration().name());
        registration.setDispatcherTypes(DISPATCHER_TYPES);
        return registration;
    }
}
