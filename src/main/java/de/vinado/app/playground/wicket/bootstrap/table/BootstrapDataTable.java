package de.vinado.app.playground.wicket.bootstrap.table;

import de.vinado.app.playground.wicket.bootstrap.table.toolbar.BootstrapAjaxHeadersToolbar;
import de.vinado.app.playground.wicket.bootstrap.table.toolbar.BootstrapAjaxNavigationToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.extensions.markup.html.repeater.data.table.NoRecordsToolbar;

import java.util.List;

public class BootstrapDataTable<T, S> extends DataTable<T, S> {

    public BootstrapDataTable(String id, List<? extends IColumn<T, S>> columns,
                              ISortableDataProvider<T, S> dataProvider, long rowsPerPage) {
        super(id, columns, dataProvider, rowsPerPage);

        addTopToolbar(new BootstrapAjaxHeadersToolbar<>(this, dataProvider));
        addBottomToolbar(new NoRecordsToolbar(this));
        addBottomToolbar(new BootstrapAjaxNavigationToolbar(this));
    }
}
