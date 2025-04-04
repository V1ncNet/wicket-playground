package de.vinado.app.playground.wicket.navigation;

import de.vinado.app.playground.wicket.image.Icon;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.IGenericComponent;
import org.apache.wicket.Page;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.list.AbstractItem;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

public class Sidebar extends Border implements IGenericComponent<Stream<NavigationItem>, Sidebar> {

    @SpringBean
    private Environment environment;

    public Sidebar(String id, IModel<Stream<NavigationItem>> model) {
        super(id, model);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        RepeatingView items = items("items");
        addToBorder(items);

        AbstractLink login;
        addToBorder(login = login("login"));
        AbstractLink logout;
        addToBorder(logout = logout("logout"));
        addToBorder(hr("hr", login, logout));

        getModelObject().forEach(item -> add(item, items));
    }

    private AbstractLink login(String wicketId) {
        ExternalLink link = new ExternalLink(wicketId, "/login", "Login");
        link.setVisible(environment.matchesProfiles("oauth2") && !AbstractAuthenticatedWebSession.get().isSignedIn());
        return link;
    }

    private AbstractLink logout(String wicketId) {
        ExternalLink link = new ExternalLink(wicketId, "/logout", "Logout");
        link.setVisible(environment.matchesProfiles("oauth2") && AbstractAuthenticatedWebSession.get().isSignedIn());
        return link;
    }

    private Component hr(String wicketId, Component... components) {
        boolean anyVisible = Arrays.stream(components).anyMatch(Component::isVisible);
        WebComponent hr = new WebComponent(wicketId);
        hr.setVisible(anyVisible);
        return hr;
    }

    private RepeatingView items(String wicketId) {
        return new RepeatingView(wicketId);
    }

    private void add(NavigationItem item, RepeatingView repeater) {
        AbstractItem child = abstractItem(repeater);

        child.queue(linkFor("link", item));
        child.queue(iconFor("icon", item));
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
        } else if (!item.isEnabled()) {
            link.add(new AttributeModifier("class", "nav-link disabled"));
        } else if (!isActive(item)) {
            link.add(new AttributeModifier("class", "nav-link link-dark"));
        }
        return link;
    }

    private boolean isActive(NavigationItem item) {
        return Objects.equals(item.getPage(), getPage().getPageClass());
    }

    private Icon iconFor(String wicketId, NavigationItem item) {
        Icon icon = new Icon(wicketId, item::getIcon);
        icon.add(AttributeAppender.append("class", "me-2"));
        return icon;
    }

    private Label labelFor(String wicketId, NavigationItem item) {
        return new Label(wicketId, item.getLabel());
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        response.render(NavigationCssResourceReference.asHeaderItem());
    }
}
