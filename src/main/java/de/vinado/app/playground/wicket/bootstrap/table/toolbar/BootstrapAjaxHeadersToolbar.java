package de.vinado.app.playground.wicket.bootstrap.table.toolbar;

import de.vinado.app.playground.wicket.bootstrap.table.sort.BootstrapAjaxOrderBorder;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;
import org.apache.wicket.markup.html.WebMarkupContainer;

public class BootstrapAjaxHeadersToolbar<S> extends HeadersToolbar<S> {

    public BootstrapAjaxHeadersToolbar(DataTable<?, S> table, ISortStateLocator<S> stateLocator) {
        super(table, stateLocator);
        table.setOutputMarkupId(true);
    }

    @Override
    protected WebMarkupContainer newSortableHeader(String headerId, S property, ISortStateLocator<S> locator) {
        return new SortableHeader(headerId, property, locator);
    }


    private class SortableHeader extends BootstrapAjaxOrderBorder<S> {

        public SortableHeader(String id, S property, ISortStateLocator<S> locator) {
            super(id, property, locator);
        }

        @Override
        protected void onClick(AjaxRequestTarget target) {
            DataTable<?, ?> table = getTable();
            target.add(table);
        }

        @Override
        protected void onSortChanged() {
            DataTable<?, ?> table = getTable();
            table.setCurrentPage(0);
        }
    }
}
