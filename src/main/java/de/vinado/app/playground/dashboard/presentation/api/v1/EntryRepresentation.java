package de.vinado.app.playground.dashboard.presentation.api.v1;

import de.vinado.app.playground.dashboard.model.Entry;

import java.util.UUID;

public record EntryRepresentation(UUID id, String text) {

    public EntryRepresentation(Entry entry) {
        this(id(entry.id()),
            text(entry));
    }

    private static UUID id(Entry.Id id) {
        return id.value();
    }

    private static String text(Entry entry) {
        return entry.text().orElse(null);
    }
}
