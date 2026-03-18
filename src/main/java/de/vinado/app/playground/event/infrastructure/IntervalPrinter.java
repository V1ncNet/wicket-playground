package de.vinado.app.playground.event.infrastructure;

import de.vinado.app.playground.event.model.Event;
import de.vinado.app.playground.event.model.Event.Interval;
import org.springframework.format.Printer;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

@Component
class IntervalPrinter implements Printer<Event.Interval> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter
        .ofLocalizedDateTime(FormatStyle.SHORT);

    @Override
    public String print(Interval interval, Locale locale) {
        DateTimeFormatter formatter = FORMATTER.localizedBy(locale);
        return formatter.format(interval.start()) + " – " + formatter.format(interval.end());
    }
}
