package de.vinado.app.playground.upload.adapter.infrastructure.client;

import de.vinado.app.playground.upload.adapter.app.UploadClientFactory;
import de.vinado.app.playground.upload.client.model.UploadClient;
import de.vinado.app.playground.upload.client.support.LoggingUploadClient;
import de.vinado.app.playground.upload.client.support.LoggingUploadClientProperties;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Random;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class LoggingUploadClientFactory implements UploadClientFactory {

    private final LoggingUploadClientProperties properties = new LoggingUploadClientProperties();

    @PostConstruct
    public void postConstruct() {
        Random random = new Random();
        properties.setCooldown(() -> {
            int cooldown = random.nextInt(200, (int) (1e4 + 1));
            return Duration.ofMillis(cooldown);
        });
    }

    @Override
    public UploadClient create() {
        return new LoggingUploadClient(properties);
    }
}
