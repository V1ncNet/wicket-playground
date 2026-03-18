package de.vinado.app.playground;

import de.vinado.app.playground.event.model.Event;
import de.vinado.app.playground.event.model.Event.Interval;
import de.vinado.app.playground.event.model.EventRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class DatabasePopulator {

    private final EventRepository eventRepository;

    // @checkstyle:off: LineLength
    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationEvent() {
        List<Event> events = new ArrayList<>();
        events.add(createEvent("Rehearsal Weekend", "Wernigerode", LocalDate.of(2026, Month.JANUARY, 16)));
        events.add(createEvent("Rehearsal Weekend", "Wernigerode", LocalDate.of(2026, Month.FEBRUARY, 13)));
        events.add(createEvent("Rehearsal Weekend", "Wernigerode", LocalDate.of(2026, Month.MARCH, 13)));
        events.add(createEvent("Concert Weekend", null, LocalDate.of(2026, Month.APRIL, 10)));
        events.add(createEvent("Concert Weekend", "Wernigerode", LocalDate.of(2026, Month.APRIL, 30)));
        events.add(createEvent("Alumni Reunion", "Wernigerode", LocalDate.of(2026, Month.JUNE, 5)));
        events.add(createEvent("Rehearsal Weekend", "Wernigerode", LocalDate.of(2026, Month.JUNE, 19), LocalDate.of(2026, Month.JUNE, 20)));
        events.add(createEvent("State Choir Competition", "Dessau", LocalDate.of(2026, Month.JUNE, 21), LocalDate.of(2026, Month.JUNE, 21)));
        eventRepository.saveAll(events);
    }
    // @checkstyle:on: LineLength

    private Event createEvent(String summary, String location, LocalDate startDate) {
        return createEvent(summary, location, startDate, startDate.plusDays(2));
    }

    private Event createEvent(String summary, String location, LocalDate startDate, LocalDate endDate) {
        ZoneId zoneId = ZoneId.systemDefault();
        Instant startInstant = startDate.atStartOfDay(zoneId).toInstant();
        Instant endInstant = endDate.atStartOfDay(zoneId).toInstant();

        Event event = new Event(UUID.randomUUID(), new Interval(startInstant, zoneId, endInstant, zoneId));
        event.setSummary(summary);
        event.setLocation(location);
        return event;
    }
}
