package de.vinado.app.playground.upload.adapter.app;

import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(fluent = false)
@Configuration
public class UploadHandlerProperties {

    private int concurrentUploads = 3;

    private int latchSize = 1;

    private int maxRetries = 3;
}
