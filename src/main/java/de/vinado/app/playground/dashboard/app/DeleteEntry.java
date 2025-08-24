package de.vinado.app.playground.dashboard.app;

import de.vinado.app.playground.dashboard.model.Entry;

import lombok.NonNull;

public record DeleteEntry(@NonNull Payload payload) {

    public record Payload(@NonNull Entry entry) {
    }
}
