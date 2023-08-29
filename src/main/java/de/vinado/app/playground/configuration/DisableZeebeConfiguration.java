package de.vinado.app.playground.configuration;

import io.camunda.zeebe.spring.client.CamundaAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "zeebe.client.enabled", havingValue = "false")
@EnableAutoConfiguration(exclude = CamundaAutoConfiguration.class)
public class DisableZeebeConfiguration {
}
