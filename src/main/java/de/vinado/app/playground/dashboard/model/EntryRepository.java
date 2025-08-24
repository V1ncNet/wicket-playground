package de.vinado.app.playground.dashboard.model;

import java.util.stream.Stream;

import lombok.NonNull;

public interface EntryRepository {

    void create(@NonNull Entry entry) throws IdAlreadyAssignedException;

    Stream<Entry> findAll();


    class IdAlreadyAssignedException extends RuntimeException {

        public IdAlreadyAssignedException(@NonNull Entry.Id id) {
            super(id + " already assigned to another entry");
        }
    }
}
