package de.vinado.app.playground.dashboard.presentation.api.v1;

import de.vinado.app.playground.dashboard.model.Entry;

import java.util.Collection;

record EntryCollectionResponse(EntryRepresentation[] entries) {

    public EntryCollectionResponse(Collection<Entry> entries) {
        this(entries.stream().map(EntryRepresentation::new).toArray(EntryRepresentation[]::new));
    }
}
