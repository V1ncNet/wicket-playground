package de.vinado.app.playground.dashboard.app;

import de.vinado.app.playground.dashboard.model.Entry;

import lombok.NonNull;

public record CreateEntry(@NonNull Payload payload) {

    public record Payload(@NonNull Entry.Id id, String text) {
    }
}
