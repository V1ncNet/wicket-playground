package de.vinado.app.playground.upload.adapter.model;

import de.vinado.app.playground.upload.overview.model.UploadResult;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NonNull;
import lombok.Setter;

@Data
public class Bundle {

    @NonNull
    private UploadResult result;

    @NonNull
    private final List<URI> fileUris;

    @Setter(AccessLevel.NONE)
    private String errorMessage;

    @Setter(AccessLevel.NONE)
    private boolean completed;

    public Optional<String> errorMessage() {
        return Optional.ofNullable(errorMessage);
    }

    public void markFailed(@NonNull String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean failed() {
        return null != errorMessage;
    }

    public boolean markCompleted() {
        return completed = true;
    }
}
