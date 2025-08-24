package de.vinado.app.playground.dashboard.infrastructure.memory;

import de.vinado.app.playground.dashboard.model.Entry;
import de.vinado.app.playground.dashboard.model.EntryRepository;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import lombok.NonNull;

@Repository
class InMemoryEntryRepository implements EntryRepository {

    private final Map<Entry.Id, Entry.State> entities = new LinkedHashMap<>();

    @Override
    public void create(@NonNull Entry entry) throws IdAlreadyAssignedException {
        assertAbsent(entry);
        save(entry);
    }

    @Override
    public Stream<Entry> findAll() {
        return entities.entrySet().stream()
            .map(entry -> new Entry(entry.getKey(), entry.getValue()));
    }

    @Override
    public Optional<Entry> findBy(@NonNull Entry.Id id) {
        return Optional.ofNullable(entities.get(id))
            .map(state -> new Entry(id, state));
    }

    @Override
    public void delete(@NonNull Entry entity) {
        entities.remove(entity.id());
    }

    private void save(Entry entity) {
        entities.put(entity.id(), entity.state());
    }

    private void assertAbsent(Entry entity) throws IdAlreadyAssignedException {
        if (isStored(entity)) {
            throw new IdAlreadyAssignedException(entity.id());
        }
    }

    private boolean isStored(Entry entity) {
        return entities.containsKey(entity.id());
    }
}
