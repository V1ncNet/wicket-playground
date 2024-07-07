package de.vinado.app.playground.upload.adapter.infrastructure.client;

import de.vinado.app.playground.upload.adapter.app.UploadClientFactory;
import de.vinado.app.playground.upload.adapter.model.Bundle;
import de.vinado.app.playground.upload.adapter.model.UploadAdapter;
import de.vinado.app.playground.upload.client.model.Transfer;
import de.vinado.app.playground.upload.client.model.UploadClient;
import de.vinado.app.playground.upload.client.model.UploadException;
import de.vinado.app.playground.upload.client.model.UploadListener;
import de.vinado.app.playground.upload.overview.model.UploadResult;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientUploadAdapter implements UploadAdapter {

    private final UploadClientFactory clientFactory;

    @Override
    public void upload(@NonNull Bundle bundle) {
        UploadClient client = clientFactory.create();
        Transfer transfer = createTransfer(bundle);
        AdapterUploadListener listener = new AdapterUploadListener(bundle);
        client.dispatch(transfer, listener);
    }

    private Transfer createTransfer(Bundle bundle) {
        Transfer transfer = new Transfer();
        populate(transfer, bundle.fileUris());
        return transfer;
    }

    private void populate(Transfer transfer, List<URI> fileUris) {
        fileUris.stream()
            .map(Paths::get)
            .forEach(transfer::add);
    }


    private record AdapterUploadListener(Bundle bundle) implements UploadListener {

        @Override
        public void onUploadCompleted(@NonNull UUID id) {
            UploadResult result = bundle.result();
            result.transferId(id);
        }

        @Override
        public void onUploadInterrupted(@NonNull UploadException e) {
            bundle.markFailed(e.getMessage());
        }
    }
}
