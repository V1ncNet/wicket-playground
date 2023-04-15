package de.vinado.app.playground.wicket;

import lombok.Getter;
import lombok.Setter;
import org.apache.wicket.RuntimeConfigurationType;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static org.apache.wicket.RuntimeConfigurationType.DEVELOPMENT;

@Getter
@Setter
@ConfigurationProperties("app.wicket")
public class WicketProperties {

    private RuntimeConfigurationType runtimeConfiguration = DEVELOPMENT;
}
