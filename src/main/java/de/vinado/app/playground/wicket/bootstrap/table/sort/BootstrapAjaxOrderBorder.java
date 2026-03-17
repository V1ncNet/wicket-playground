package de.vinado.app.playground.wicket.bootstrap.table.sort;

import de.vinado.app.playground.wicket.bootstrap.icon.Bi;
import de.vinado.app.playground.wicket.image.Icon;
import de.vinado.app.playground.wicket.image.IconType;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.sort.AjaxOrderByLink;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortState;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public abstract class BootstrapAjaxOrderBorder<S> extends Border {

    public BootstrapAjaxOrderBorder(String id, S property, ISortStateLocator<S> locator) {
        super(id);

        Link link = new Link("link", property, locator);
        addToBorder(link);

        IModel<IconType> model = Model.of();
        Icon icon = new Icon("icon", model);
        icon.setVisible(false);
        link.add(icon);

        ISortState<S> sortState = locator.getSortState();
        SortOrder sortOrder = sortState.getPropertySortOrder(property);
        if (SortOrder.ASCENDING.equals(sortOrder)) {
            icon.setVisible(true);
            model.setObject(Bi.CHEVRON_UP);
        } else if (SortOrder.DESCENDING.equals(sortOrder)) {
            icon.setVisible(false);
            model.setObject(Bi.CHEVRON_DOWN);
        } else {
            icon.setVisible(true);
            model.setObject(Bi.CHEVRON_EXPAND);
        }
    }

    protected abstract void onClick(AjaxRequestTarget target);

    protected abstract void onSortChanged();


    private class Link extends AjaxOrderByLink<S> {

        public Link(String id, S sortProperty, ISortStateLocator<S> stateLocator) {
            super(id, sortProperty, stateLocator);
        }

        @Override
        public void onClick(AjaxRequestTarget target) {
            BootstrapAjaxOrderBorder.this.onClick(target);
        }

        @Override
        protected void onSortChanged() {
            BootstrapAjaxOrderBorder.this.onSortChanged();
        }
    }
}
