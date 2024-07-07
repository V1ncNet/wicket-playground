package de.vinado.app.playground.upload.client.model;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.NonNull;
import lombok.Value;

@Value
public class Transfer {

    @NonNull
    UUID id = UUID.randomUUID();

    List<Path> paths = new ArrayList<>();

    public void add(Path path) {
        paths.add(path);
    }
}
