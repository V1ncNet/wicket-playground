package de.vinado.app.playground.wicket.bootstrap.table.toolbar;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.NavigatorLabel;
import org.apache.wicket.markup.html.WebMarkupContainer;

import java.util.List;

public class BootstrapAjaxNavigationToolbar extends AbstractToolbar {

    public BootstrapAjaxNavigationToolbar(DataTable<?, ?> table) {
        super(table);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        WebMarkupContainer span = new WebMarkupContainer("span");
        add(span);

        span.add(AttributeModifier.replace("colspan", this::colspan));

        BootstrapAjaxPagingNavigator pagingNavigator = new BootstrapAjaxPagingNavigator("navigator", getTable());
        span.add(pagingNavigator);

        NavigatorLabel navigatorLabel = new NavigatorLabel("label", getTable());
        span.add(navigatorLabel);
    }

    private String colspan() {
        DataTable<?, ?> table = getTable();
        List<? extends IColumn<?, ?>> columns = table.getColumns();
        int size = columns.size();
        return String.valueOf(size);
    }

    @Override
    protected void onConfigure() {
        super.onConfigure();

        DataTable<?, ?> table = getTable();
        long pageCount = table.getPageCount();
        setVisible(pageCount > 1);
    }
}
