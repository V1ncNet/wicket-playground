package de.vinado.app.playground.event.presentation.ui;

import de.vinado.app.playground.data.jpa.OffsetPageable;
import de.vinado.app.playground.event.model.Event;
import de.vinado.app.playground.event.model.EventRepository;
import de.vinado.app.playground.event.model.Event_;
import de.vinado.app.playground.event.model.Event_.Interval_;
import de.vinado.app.playground.wicket.bootstrap.table.BootstrapDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.repeater.ReuseIfModelsEqualStrategy;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.JpaSort;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

import static org.springframework.data.jpa.domain.JpaSort.path;

public class EventsTable extends BootstrapDataTable<Event, String> {

    public EventsTable(String id, List<? extends IColumn<Event, String>> columns,
                       ISortableDataProvider<Event, String> dataProvider, long rowsPerPage) {
        super(id, columns, dataProvider, rowsPerPage);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        setItemReuseStrategy(ReuseIfModelsEqualStrategy.getInstance());
    }


    @RequiredArgsConstructor
    public static class DataProvider extends SortableDataProvider<Event, String> {

        private final EventRepository eventRepository;

        @Override
        public Iterator<? extends Event> iterator(long first, long count) {
            Sort sort = getSpringSort();
            Pageable pageable = new OffsetPageable(first, count, sort);

            Page<Event> result = eventRepository.findAll(pageable);
            List<Event> content = result.getContent();
            return content.iterator();
        }

        private Sort getSpringSort() {
            SortParam<String> param = getSort();
            return Optional.ofNullable(param)
                .map(this::toSpringSort)
                .orElseGet(Sort::unsorted);
        }

        private Sort toSpringSort(SortParam<String> param) {
            Direction direction = getDirection(param);
            String property = getProperty(param);
            Sort primary = JpaSort.unsafe(direction, property);
            Sort secondary = JpaSort.of(Direction.ASC, path(Event_.interval).dot(Interval_.startInstant));
            return primary.and(secondary);
        }

        private Direction getDirection(SortParam<String> param) {
            return param.isAscending()
                ? Direction.ASC
                : Direction.DESC;
        }

        private String getProperty(SortParam<String> param) {
            return param.getProperty();
        }

        @Override
        public long size() {
            return eventRepository.count();
        }

        @Override
        public IModel<Event> model(Event object) {
            return new EventModel(object);
        }


        private static class EventModel extends Model<Event> {

            public EventModel(Event object) {
                super(object);
            }

            @Override
            public int hashCode() {
                Event object = getObject();
                return Objects.hash(
                    object.getId(),
                    object.getSummary(),
                    object.getLocation(),
                    object.getInterval());
            }
        }
    }
}
