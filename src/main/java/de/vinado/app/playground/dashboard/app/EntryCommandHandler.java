package de.vinado.app.playground.dashboard.app;

import de.vinado.app.playground.dashboard.model.Entry;
import de.vinado.app.playground.dashboard.model.EntryRepository;
import org.springframework.stereotype.Service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EntryCommandHandler {

    private final EntryRepository entryRepository;

    public void execute(@NonNull CreateEntry command) {
        CreateEntry.Payload payload = command.payload();
        Entry entry = createEntry(payload);
        mutate(entry, payload);
        entryRepository.create(entry);
        log.debug("Created {}", entry);
    }

    public void execute(@NonNull DeleteEntry command) {
        DeleteEntry.Payload payload = command.payload();
        Entry entry = payload.entry();
        entryRepository.delete(entry);
        log.debug("Deleted {}", entry);
    }

    private Entry createEntry(CreateEntry.Payload payload) {
        Entry.Id id = payload.id();
        String text = payload.text();
        return new Entry(id, text);
    }

    private void mutate(Entry entry, CreateEntry.Payload payload) {
        entry.mutate()
            .text(payload.text())
        ;
    }
}
