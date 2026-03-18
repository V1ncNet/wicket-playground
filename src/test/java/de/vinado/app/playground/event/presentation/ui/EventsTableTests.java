package de.vinado.app.playground.event.presentation.ui;

import de.vinado.app.playground.event.model.Event;
import de.vinado.app.playground.event.model.Event.Interval;
import de.vinado.app.playground.event.model.EventRepository;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class EventsTableTests {

    @Test
    void iterator_shouldRespectOffsetOnLastPage() {
        EventRepository repository = mock(EventRepository.class);
        List<Event> events = IntStream.range(0, 8)
            .mapToObj(this::event)
            .toList();

        when(repository.findAll(any(Pageable.class))).thenAnswer(invocation -> {
            Pageable pageable = invocation.getArgument(0, Pageable.class);
            int start = Math.toIntExact(pageable.getOffset());
            int end = Math.min(start + pageable.getPageSize(), events.size());
            List<Event> pageContent = events.subList(start, end);
            return new PageImpl<>(pageContent, pageable, events.size());
        });

        EventsTable.DataProvider provider = new EventsTable.DataProvider(repository);

        Iterator<? extends Event> iterator = provider.iterator(5, 3);

        assertEquals(List.of(events.get(5), events.get(6), events.get(7)), toList(iterator));
    }

    private Event event(int index) {
        Instant start = Instant.parse("2026-01-01T00:00:00Z").plusSeconds(index * 3600L);
        Instant end = start.plusSeconds(1800L);
        Interval interval = new Interval(start, ZoneOffset.UTC, end, ZoneOffset.UTC);
        Event event = new Event(UUID.randomUUID(), interval);
        event.setSummary("Event " + index);
        event.setLocation("Location " + index);
        return event;
    }

    private static List<Event> toList(Iterator<? extends Event> iterator) {
        List<Event> events = new ArrayList<>();
        iterator.forEachRemaining(events::add);
        return events;
    }
}
