package de.vinado.app.playground.dashboard.presentation.api.v1;

import de.vinado.app.playground.dashboard.app.CreateEntry;
import de.vinado.app.playground.dashboard.app.DeleteEntry;
import de.vinado.app.playground.dashboard.app.EntryCommandHandler;
import de.vinado.app.playground.dashboard.model.Entry;
import de.vinado.app.playground.dashboard.model.EntryIdFactory;
import de.vinado.app.playground.dashboard.model.EntryRepository;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(EntryRestController.PATH)
@RequiredArgsConstructor
class EntryRestController {

    public static final String PATH = DashboardRestController.PATH + "/entries";

    private final EntryIdFactory entryIdFactory;

    private final EntryCommandHandler entryCommandHandler;

    private final EntryRepository entryRepository;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> insert(@RequestBody EntryRepresentation rep) {
        Entry.Id id = entryIdFactory.create();
        create(id, rep);
        URI location = personResourceLocation(id);
        return ResponseEntity.created(location).build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntryCollectionResponse> list() {
        return entryRepository.findAll()
            .collect(Collectors.collectingAndThen(toEntryCollectionResponse(), ResponseEntity::ok));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID resourceId) {
        Entry.Id id = new Entry.Id(resourceId);
        entryRepository.findBy(id)
            .ifPresent(this::delete);
        return ResponseEntity.noContent().build();
    }

    private void create(Entry.Id id, EntryRepresentation rep) {
        CreateEntry command = createEntryCommand(id, rep);
        entryCommandHandler.execute(command);
    }

    private void delete(Entry entry) {
        DeleteEntry command = deleteEntryCommand(entry);
        entryCommandHandler.execute(command);
    }

    private CreateEntry createEntryCommand(Entry.Id id, EntryRepresentation rep) {
        CreateEntry.Payload payload = new CreateEntry.Payload(id, rep.text());
        return new CreateEntry(payload);
    }

    private DeleteEntry deleteEntryCommand(Entry entry) {
        DeleteEntry.Payload payload = new DeleteEntry.Payload(entry);
        return new DeleteEntry(payload);
    }

    private URI personResourceLocation(Entry.Id id) {
        return ServletUriComponentsBuilder.fromPath(PATH)
            .path("/{id}")
            .buildAndExpand(id)
            .toUri();
    }

    private static Collector<Entry, ?, EntryCollectionResponse> toEntryCollectionResponse() {
        return Collectors.collectingAndThen(Collectors.toList(), EntryCollectionResponse::new);
    }
}
