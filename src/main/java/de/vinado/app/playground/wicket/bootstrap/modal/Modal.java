package de.vinado.app.playground.wicket.bootstrap.modal;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes.EventPropagation;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.TransparentWebMarkupContainer;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.string.Strings;
import org.danekja.java.util.function.serializable.SerializableFunction;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

public class Modal extends Panel {

    public static final String CONTENT_WICKET_ID = "content";
    public static final String ACTION_WICKET_ID = "action";

    private final Component title;
    private final WebMarkupContainer header;
    private final WebMarkupContainer body;
    private final WebMarkupContainer footer;
    private final List<Component> actions = new LinkedList<>();

    private Size size = Size.DEFAULT;

    public Modal(String id) {
        super(id);

        this.title = title("title");
        this.header = header("header");
        this.body = body("body");
        this.footer = footer("footer");
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        setOutputMarkupPlaceholderTag(true);
        setVisible(false);

        title.setOutputMarkupId(true);

        header.add(title);
        header.add(closeButton("closeButton"));
        footer.add(actions("actions"));

        WebMarkupContainer dialog;
        add(dialog = dialog("dialog"));
        dialog.add(header, body, footer);

        add(new ModalCloseBehavior());
    }

    @Override
    protected void onComponentTag(ComponentTag tag) {
        super.onComponentTag(tag);

        tag.put("class", "modal fade");
        tag.put("tabindex", "-1");
        tag.put("aria-labelledby", title.getMarkupId());
    }

    @Override
    protected void onConfigure() {
        super.onConfigure();

        if (Strings.isEmpty(title.getDefaultModelObjectAsString())) {
            title.setDefaultModelObject("&nbsp;");
            title.setEscapeModelStrings(false);
        }

        footer.setVisible(!actions.isEmpty());
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);

        String markupId = getMarkupId(true);
        String initializationScript = createInitializationScript(markupId);
        response.render(OnDomReadyHeaderItem.forScript(initializationScript));
    }

    protected WebMarkupContainer dialog(String wicketId) {
        return new Dialog(wicketId);
    }

    protected Component title(String wicketId) {
        return new Label(wicketId, Model.of());
    }

    protected WebMarkupContainer header(String wicketId) {
        return new WebMarkupContainer(wicketId);
    }

    protected WebMarkupContainer body(String wicketId) {
        return new WebMarkupContainer(wicketId);
    }

    protected WebMarkupContainer footer(String wicketId) {
        return new WebMarkupContainer(wicketId);
    }

    protected Component closeButton(String wicketId) {
        return new CloseButton(wicketId);
    }

    protected ListView<Component> actions(String wicketId) {
        return new ListView<>(wicketId, actions) {

            @Override
            protected void populateItem(ListItem<Component> item) {
                item.add(item.getModelObject());
            }
        };
    }

    public Modal hideHeader(boolean hide) {
        header.setVisible(!hide);
        return this;
    }

    public Modal size(Size size) {
        this.size = size;
        return this;
    }

    public Modal title(IModel<?> title) {
        this.title.setDefaultModel(title);
        return this;
    }

    public <T extends Component> Modal content(SerializableFunction<String, T> constructor) {
        Component component = constructor.apply(CONTENT_WICKET_ID);
        return content(component);
    }

    public Modal content(Component component) {
        if (!CONTENT_WICKET_ID.equals(component.getId())) {
            throw new IllegalArgumentException("Invalid content Wicket ID. Must be '" + CONTENT_WICKET_ID + "'.");
        }

        component.setRenderBodyOnly(true);
        body.addOrReplace(component);
        return this;
    }

    public Modal show(AjaxRequestTarget target) {
        assertContent();
        showModal(target);
        appendShowDialogJavaScript(target);
        return this;
    }

    private void assertContent() {
        if (null == body.get(CONTENT_WICKET_ID)) {
            throw new WicketRuntimeException("Missing modal content. Use modal.content(...).show(...)");
        }
    }

    private void showModal(AjaxRequestTarget target) {
        setVisible(true);
        target.add(this);
    }

    public Modal hide(AjaxRequestTarget target) {
        appendHideDialogJavaScript(target);
        return this;
    }

    public Modal addAction(SerializableFunction<String, Action> constructor) {
        Action button = constructor.apply(ACTION_WICKET_ID);
        return addAction(button);
    }

    public Modal addAction(Action action) {
        if (!ACTION_WICKET_ID.equals(action.getId())) {
            throw new IllegalArgumentException("Invalid action Wicket ID. Must be '" + ACTION_WICKET_ID + "'.");
        }

        actions.add(action);
        return this;
    }

    public Modal clearActions() {
        actions.clear();
        return this;
    }

    protected void appendShowDialogJavaScript(AjaxRequestTarget target) {
        target.appendJavaScript(createActionScript(getMarkupId(true), "show"));
    }

    protected void appendHideDialogJavaScript(AjaxRequestTarget target) {
        target.prependJavaScript(createActionScript(getMarkupId(true), "hide"));
    }

    protected static String createInitializationScript(String markupId) {
        return "new bootstrap.Modal(document.getElementById('" + markupId + "'));";
    }

    protected static String createActionScript(String markupId, String action) {
        return "bootstrap.Modal.getInstance(document.getElementById('" + markupId + "'))." + action + "();";
    }


    @RequiredArgsConstructor(access = AccessLevel.PACKAGE)
    public enum Size {

        SMALL("modal-sm"),
        DEFAULT(""),
        LARGE("modal-lg"),
        EXTRA_LARGE("modal-xl"),
        ;

        private final String cssClassName;
    }


    public static abstract class Action extends TransparentWebMarkupContainer {

        private final IModel<String> label;
        private final Color color;

        public Action(String id, IModel<String> label) {
            this(id, label, Color.SECONDARY);
        }

        public Action(String id, IModel<String> label, Color color) {
            super(id);

            this.label = label;
            this.color = color;
        }

        @Override
        protected void onInitialize() {
            super.onInitialize();

            add(button()
                .add(label())
                .add(color()));
        }

        private AbstractLink button() {
            AbstractLink button = button("button");
            configure(button);
            return button;
        }

        protected abstract AbstractLink button(String wicketId);

        protected void configure(AbstractLink link) {
            link.add(new AttributeModifier("type", "button"));
        }

        private Behavior color() {
            return new AttributeModifier("class", "btn " + color.cssClassName);
        }

        private Label label() {
            return new Label("label", label);
        }


        @RequiredArgsConstructor(access = AccessLevel.PACKAGE)
        public enum Color {

            PRIMARY("btn-primary"),
            SECONDARY("btn-secondary"),
            SUCCESS("btn-success"),
            DANGER("btn-danger"),
            WARNING("btn-warning"),
            INFO("btn-info"),
            LIGHT("btn-light"),
            DARK("btn-dark"),
            LINK("btn-link"),
            ;

            private final String cssClassName;
        }
    }

    public static abstract class AjaxAction extends Action {

        public AjaxAction(String id, IModel<String> label) {
            super(id, label, Color.SECONDARY);
        }

        public AjaxAction(String id, IModel<String> label, Color color) {
            super(id, label, color);
        }

        @Override
        protected AbstractLink button(String wicketId) {
            return new AjaxLink<Void>(wicketId) {

                @Override
                public void onClick(AjaxRequestTarget target) {
                    AjaxAction.this.onClick(target);
                }
            };
        }

        protected abstract void onClick(AjaxRequestTarget target);
    }


    public static class CloseAction extends Action {

        public CloseAction(String id, IModel<String> label) {
            this(id, label, Color.SECONDARY);
        }

        public CloseAction(String id, IModel<String> label, Color color) {
            super(id, label, color);
        }

        @Override
        protected AbstractLink button(String wicketId) {
            return new CloseButton(wicketId);
        }
    }

    public static class SubmitAction extends Action {

        public SubmitAction(String id, IModel<String> label) {
            this(id, label, Color.PRIMARY);
        }

        public SubmitAction(String id, IModel<String> label, Color color) {
            super(id, label, color);
        }

        @Override
        protected AbstractLink button(String wicketId) {
            Form<?> form = form().orElse(null);
            return new AjaxSubmitLink(wicketId, form) {

                @Override
                protected void onSubmit(AjaxRequestTarget target) {
                    SubmitAction.this.onSubmit(target);
                }
            };
        }

        @Override
        protected void configure(AbstractLink link) {
            super.configure(link);

            link.add(new AttributeModifier("type", "submit"));
            form().ifPresent(form -> link.add(new AttributeModifier("form", form.getMarkupId(true))));
        }

        @SuppressWarnings("rawtypes")
        private Optional<Form> form() {
            Modal modal = findParent(Modal.class);
            return modal.streamChildren()
                .filter(Form.class::isInstance)
                .map(Form.class::cast)
                .findFirst();
        }

        protected void onSubmit(AjaxRequestTarget target) {
            Modal modal = findParent(Modal.class);
            modal.appendHideDialogJavaScript(target);
        }
    }

    private static class CloseButton extends AjaxLink<Void> {

        public CloseButton(String id) {
            super(id);
        }

        @Override
        protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
            super.updateAjaxAttributes(attributes);

            Modal modal = findParent(Modal.class);
            String modalMarkupId = modal.getMarkupId();
            AjaxCallListener listener = new AjaxCallListener();
            listener.onBeforeSend(createActionScript(modalMarkupId, "hide"));
            attributes.getAjaxCallListeners().add(listener);
        }

        @Override
        public void onClick(AjaxRequestTarget target) {
        }
    }


    private class Dialog extends TransparentWebMarkupContainer {

        public Dialog(String id) {
            super(id);
        }

        @Override
        protected void onComponentTag(ComponentTag tag) {
            super.onComponentTag(tag);

            tag.put("class", "modal-dialog " + size.cssClassName);
        }
    }

    private class ModalCloseBehavior extends AjaxEventBehavior {

        public ModalCloseBehavior() {
            super("hidden.bs.modal");
        }

        @Override
        protected void onEvent(AjaxRequestTarget target) {
            if (isVisibleInHierarchy()) {
                handleCloseEvent(target);
            }
        }

        @Override
        protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
            super.updateAjaxAttributes(attributes);

            attributes.setEventPropagation(EventPropagation.BUBBLE);
        }

        private void handleCloseEvent(AjaxRequestTarget target) {
            resetComponents();
            hideDialog(target);
        }

        private void resetComponents() {
            clearActions();
        }

        private void hideDialog(AjaxRequestTarget target) {
            Modal modal = Modal.this;
            modal.setVisible(false);
            target.add(modal);
        }
    }
}
