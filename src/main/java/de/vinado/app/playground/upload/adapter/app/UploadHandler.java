package de.vinado.app.playground.upload.adapter.app;

import de.vinado.app.playground.upload.adapter.model.Bundle;
import de.vinado.app.playground.upload.adapter.model.Request;
import de.vinado.app.playground.upload.adapter.model.UploadAdapter;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class UploadHandler {

    private final UploadAdapter uploadAdapter;
    private final UploadHandlerProperties properties;
    private final ExecutorService executorService;

    public UploadHandler(UploadAdapter uploadAdapter, UploadHandlerProperties properties) {
        this.uploadAdapter = uploadAdapter;
        this.properties = properties;
        this.executorService = Executors.newFixedThreadPool(properties.getConcurrentUploads());
    }

    public void handle(@NonNull Request request) {
        List<Bundle> bundles = request.listPending();
        int bundleSize = bundles.size();
        int latchSize = properties.getLatchSize();
        int totalGroups = (int) Math.ceil((double) bundleSize / latchSize);

        CountDownLatch latch = new CountDownLatch(totalGroups);
        for (int i = 0; i < bundleSize; i += latchSize) {
            int end = Math.min(bundleSize, i + latchSize);
            List<Bundle> batch = bundles.subList(i, end);
            int maxRetries = properties.getMaxRetries();
            UploadTask task = new UploadTask(this, batch, latch, maxRetries);
            executorService.submit(task);
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Upload was interrupted", e);
        }
    }


    @RequiredArgsConstructor
    private static class UploadTask implements Runnable {

        private final UploadHandler initiator;
        private final List<Bundle> bundles;
        private final CountDownLatch latch;
        private final int maxRetries;

        @Override
        public void run() {
            for (Bundle bundle : bundles) {
                upload(bundle);
            }
            latch.countDown();
        }

        private void upload(Bundle bundle) {
            int attempts = 0;

            while (attempts < maxRetries && !bundle.isCompleted()) {
                initiator.uploadAdapter.upload(bundle);
                attempts++;
            }
        }
    }
}
