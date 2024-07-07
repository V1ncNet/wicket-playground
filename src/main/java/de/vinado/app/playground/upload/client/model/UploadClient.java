package de.vinado.app.playground.upload.client.model;

import lombok.NonNull;

public interface UploadClient {

    void dispatch(@NonNull Transfer transfer, @NonNull UploadListener listener);
}
