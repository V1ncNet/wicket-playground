package de.vinado.app.playground.upload.client.support;

import java.time.Duration;
import java.util.function.Supplier;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(fluent = false)
public class LoggingUploadClientProperties {

    private Supplier<Duration> cooldown;
}
