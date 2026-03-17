package de.vinado.app.playground.wicket.bootstrap.table.toolbar;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.markup.html.IAjaxLink;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigation;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigationBehavior;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigationIncrementLink;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigationLink;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.TransparentWebMarkupContainer;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.LoopItem;
import org.apache.wicket.markup.html.navigation.paging.IPageable;
import org.apache.wicket.markup.html.navigation.paging.IPagingLabelProvider;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigation;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;

import java.util.ArrayList;
import java.util.List;

class BootstrapAjaxPagingNavigator extends PagingNavigator {

    private final DataTable<?, ?> table;

    public BootstrapAjaxPagingNavigator(String id, DataTable<?, ?> table) {
        super(id, table);
        this.table = table;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        setOutputMarkupId(true);

        add(new PagingItem("firstItem", "first"));
        add(new PagingItem("prevItem", "prev"));
        add(new PagingItem("nextItem", "next"));
        add(new PagingItem("lastItem", "last"));
    }

    @Override
    protected void onComponentTag(ComponentTag tag) {
        super.onComponentTag(tag);

        tag.put("class", "pagination pagination-sm");
    }

    @Override
    protected PagingNavigation newNavigation(String id, IPageable pageable, IPagingLabelProvider labelProvider) {
        return new BootstrapAjaxPagingNavigation(id, pageable, labelProvider);
    }

    @Override
    protected AbstractLink newPagingNavigationIncrementLink(String id, IPageable pageable, int increment) {
        return new BootstrapAjaxPagingNavigationIncrementLink(id, pageable, increment);
    }

    @Override
    protected AbstractLink newPagingNavigationLink(String id, IPageable pageable, int pageNumber) {
        return new BootstrapAjaxPagingNavigationLink(id, pageable, pageNumber);
    }

    protected void onAjaxEvent(AjaxRequestTarget target) {
        target.add(table);

        if (!table.contains(this, true)) {
            target.add(this);
        }
    }


    private static class PagingItem extends TransparentWebMarkupContainer {

        private final String childId;

        private PagingItem(String id, String childId) {
            super(id);
            this.childId = childId;
        }

        @Override
        protected void onComponentTag(ComponentTag tag) {
            super.onComponentTag(tag);

            List<String> cssClasses = new ArrayList<>();
            cssClasses.add("page-item");

            MarkupContainer parent = getParent();
            Component child = parent.get(childId);
            if (!child.isEnabled()) {
                cssClasses.add("disabled");
            }

            tag.put("class", String.join(" ", cssClasses));
        }
    }

    private static class BootstrapAjaxPagingNavigation extends AjaxPagingNavigation {

        public BootstrapAjaxPagingNavigation(String id, IPageable pageable, IPagingLabelProvider labelProvider) {
            super(id, pageable, labelProvider);
        }

        @Override
        protected void populateItem(final LoopItem loopItem) {
            super.populateItem(loopItem);

            long index = getStartIndex() + loopItem.getIndex();
            long currentPage = pageable.getCurrentPage();
            if (index == currentPage) {
                loopItem.add(new AttributeAppender("class", " active "));
            }
        }

        @Override
        protected Link<?> newPagingNavigationLink(String id, IPageable pageable, long pageIndex) {
            BootstrapAjaxPagingNavigationLink link = new BootstrapAjaxPagingNavigationLink(id, pageable, pageIndex);
            link.add(new AttributeAppender("class", " page-link "));
            return link;

        }
    }

    private static class BootstrapAjaxPagingNavigationLink extends AjaxPagingNavigationLink {

        public BootstrapAjaxPagingNavigationLink(String id, IPageable pageable, long pageNumber) {
            super(id, pageable, pageNumber);
        }

        @Override
        protected AjaxPagingNavigationBehavior newAjaxPagingNavigationBehavior(IPageable pageable, String event) {
            return new BootstrapAjaxPagingNavigationBehavior(this, pageable, event);
        }
    }

    private static class BootstrapAjaxPagingNavigationIncrementLink extends AjaxPagingNavigationIncrementLink {

        public BootstrapAjaxPagingNavigationIncrementLink(String id, IPageable pageable, int increment) {
            super(id, pageable, increment);
        }

        @Override
        protected AjaxPagingNavigationBehavior newAjaxPagingNavigationBehavior(IPageable pageable, String event) {
            return new BootstrapAjaxPagingNavigationBehavior(this, pageable, event);
        }
    }

    private static class BootstrapAjaxPagingNavigationBehavior extends AjaxPagingNavigationBehavior {

        private final IAjaxLink owner;

        public BootstrapAjaxPagingNavigationBehavior(IAjaxLink owner, IPageable pageable, String event) {
            super(owner, pageable, event);
            this.owner = owner;
        }

        @Override
        protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
            super.updateAjaxAttributes(attributes);
            attributes.setPreventDefault(true);
        }

        @Override
        protected void onEvent(AjaxRequestTarget target) {
            owner.onClick(target);

            BootstrapAjaxPagingNavigator navigator = ((Component) owner).findParent(BootstrapAjaxPagingNavigator.class);

            if (null != navigator) {
                navigator.onAjaxEvent(target);
            }
        }
    }
}
