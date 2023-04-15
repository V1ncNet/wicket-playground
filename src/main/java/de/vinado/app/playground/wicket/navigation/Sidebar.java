package de.vinado.app.playground.wicket.navigation;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Page;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.AbstractItem;
import org.apache.wicket.markup.repeater.RepeatingView;

import java.util.Objects;
import java.util.stream.Stream;

public abstract class Sidebar extends Border {

    public Sidebar(String id) {
        super(id);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        RepeatingView items = items("items");
        addToBorder(items);

        navigationEntries().forEach(item -> add(item, items));
    }

    private RepeatingView items(String wicketId) {
        return new RepeatingView(wicketId);
    }

    protected abstract Stream<NavigationItem> navigationEntries();

    private void add(NavigationItem item, RepeatingView repeater) {
        AbstractItem child = abstractItem(repeater);

        child.queue(linkFor("link", item));
        child.queue(labelFor("label", item));
    }

    private AbstractItem abstractItem(RepeatingView repeater) {
        AbstractItem item = new AbstractItem(repeater.newChildId());
        repeater.add(item);
        return item;
    }

    private AbstractLink linkFor(String wicketId, NavigationItem item) {
        BookmarkablePageLink<Page> link = new BookmarkablePageLink<>(wicketId, item.getPage(), item.getParameters());
        link.setEnabled(item.isEnabled());
        if (isActive(item)) {
            link.add(new AttributeModifier("class", "nav-link active"));
            link.add(new AttributeModifier("aria-current", "page"));
        } else {
            link.add(new AttributeModifier("class", "nav-link link-dark"));
        }
        return link;
    }

    private boolean isActive(NavigationItem item) {
        return Objects.equals(item.getPage(), getPage().getPageClass());
    }

    private Label labelFor(String wicketId, NavigationItem item) {
        return new Label(wicketId, item.getLabel());
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        response.render(NavigationCssResourceReference.asHeaderItem());
    }
}
