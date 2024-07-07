package de.vinado.app.playground.upload.client.support;

import de.vinado.app.playground.upload.client.model.Transfer;
import de.vinado.app.playground.upload.client.model.UploadClient;
import de.vinado.app.playground.upload.client.model.UploadException;
import de.vinado.app.playground.upload.client.model.UploadListener;

import java.nio.file.Path;
import java.time.Duration;
import java.util.function.Supplier;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class LoggingUploadClient implements UploadClient {

    private final LoggingUploadClientProperties properties;

    @Override
    public void dispatch(@NonNull Transfer transfer, @NonNull UploadListener listener) {
        try {
            for (Path path : transfer.paths()) {
                log.info("Uploaded file: {}", path);
                Supplier<Duration> cooldown = properties.getCooldown();
                Thread.sleep(cooldown.get().toMillis());
            }
            listener.onUploadCompleted(transfer.id());
        } catch (InterruptedException e) {
            UploadException exception = new UploadException(e);
            listener.onUploadInterrupted(exception);
        }
    }
}
