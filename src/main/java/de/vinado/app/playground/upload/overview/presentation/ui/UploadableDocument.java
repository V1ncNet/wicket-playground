package de.vinado.app.playground.upload.overview.presentation.ui;

import de.vinado.app.playground.upload.overview.model.UploadResult;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.Serializable;
import java.net.URI;

import lombok.NonNull;
import lombok.Value;
import lombok.experimental.Accessors;

@Value
@Accessors(fluent = false)
public class UploadableDocument implements Serializable {

    @NonNull
    URI uri;

    @NonNull
    UploadResult uploadResult = new UploadResult();

    public static UploadableDocument fromPath(String path) {
        URI uri = UriComponentsBuilder.fromUriString("file://")
            .path(path)
            .build().toUri();
        return new UploadableDocument(uri);
    }

    public boolean isUploaded() {
        return null != uploadResult.transferId();
    }
}
