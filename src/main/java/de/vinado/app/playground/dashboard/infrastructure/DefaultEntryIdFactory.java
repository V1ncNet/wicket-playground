package de.vinado.app.playground.dashboard.infrastructure;

import de.vinado.app.playground.dashboard.model.Entry;
import de.vinado.app.playground.dashboard.model.EntryIdFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
class DefaultEntryIdFactory implements EntryIdFactory {

    @Override
    public Entry.Id create() {
        UUID value = UUID.randomUUID();
        return new Entry.Id(value);
    }
}
