package de.vinado.app.playground.configuration;

import io.camunda.zeebe.spring.client.CamundaAutoConfiguration;
import io.camunda.zeebe.spring.client.annotation.Deployment;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

public class CamundaApplicationConfiguration {

    @Configuration
    @ConditionalOnProperty(name = "zeebe.client.enabled", havingValue = "false")
    @EnableAutoConfiguration(exclude = CamundaAutoConfiguration.class)
    static class DisableZeebeConfiguration {
    }

    @Configuration
    @ConditionalOnProperty(name = "zeebe.client.enabled", havingValue = "true", matchIfMissing = true)
    @Deployment(resources = "classpath*:**/*.bpmn")
    static class ProcessDeploymentConfiguration {
    }
}
