package de.vinado.app.playground.wicket;

import org.apache.wicket.RuntimeConfigurationType;
import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import static org.apache.wicket.RuntimeConfigurationType.DEVELOPMENT;

@Getter
@Setter
@Accessors(fluent = false)
@ConfigurationProperties("app.wicket")
public class WicketProperties {

    private RuntimeConfigurationType runtimeConfiguration = DEVELOPMENT;
}
