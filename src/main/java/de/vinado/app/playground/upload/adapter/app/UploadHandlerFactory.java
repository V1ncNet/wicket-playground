package de.vinado.app.playground.upload.adapter.app;

import de.vinado.app.playground.upload.adapter.model.UploadAdapter;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UploadHandlerFactory {

    private final UploadAdapter uploadAdapter;
    private final UploadHandlerProperties properties;

    public UploadHandler create() {
        return new UploadHandler(uploadAdapter, properties);
    }
}
