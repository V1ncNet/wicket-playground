package de.vinado.app.playground.bootstrap.presentation.ui.table.simple;

import de.vinado.app.playground.event.model.Event;
import de.vinado.app.playground.event.model.Event.Interval;
import de.vinado.app.playground.event.model.EventRepository;
import de.vinado.app.playground.event.presentation.ui.EventsTable;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.LambdaColumn;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.format.Printer;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SimpleBootstrapTablePanel extends Panel {

    private static final int ROWS_PER_PAGE = 5;

    @SpringBean
    private EventRepository eventRepository;

    @SpringBean
    private Printer<Interval> intervalPrinter;

    public SimpleBootstrapTablePanel(String id) {
        super(id);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        List<IColumn<Event, String>> columns = columns();
        EventsTable.DataProvider dataProvider = new EventsTable.DataProvider(eventRepository);
        dataProvider.setSort("interval.startInstant", SortOrder.ASCENDING);
        EventsTable table = new EventsTable("table", columns, dataProvider, ROWS_PER_PAGE);
        add(table);
    }

    private List<IColumn<Event, String>> columns() {
        List<IColumn<Event, String>> columns = new ArrayList<>();

        columns.add(new LambdaColumn<>(Model.of("Summary"), "summary", Event::getSummary));
        columns.add(new LambdaColumn<>(Model.of("Date"), "interval.startInstant", this::printDate));
        columns.add(new LambdaColumn<>(Model.of("Location"), "location", Event::getLocation));

        return columns;
    }

    private String printDate(Event event) {
        Interval interval = event.getInterval();
        Locale locale = getLocale();
        return intervalPrinter.print(interval, locale);
    }
}
