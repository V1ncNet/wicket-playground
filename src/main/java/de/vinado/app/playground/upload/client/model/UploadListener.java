package de.vinado.app.playground.upload.client.model;

import java.util.UUID;

import lombok.NonNull;

public interface UploadListener {

    void onUploadCompleted(@NonNull UUID id);

    void onUploadInterrupted(@NonNull UploadException e);
}
