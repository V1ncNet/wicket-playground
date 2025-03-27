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
import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
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
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.util.string.Strings;
import org.danekja.java.util.function.serializable.SerializableFunction;
import org.danekja.java.util.function.serializable.SerializableUnaryOperator;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class Modal extends Panel {

    private final Component title;
    private final WebMarkupContainer header;
    private final WebMarkupContainer body;
    private final WebMarkupContainer footer;
    private final List<Component> actions = new LinkedList<>();

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

        add(AttributeModifier.append("class", "modal fade"));
        add(AttributeModifier.append("tabindex", "-1"));
        add(AttributeModifier.append("aria-labelledby", title.getMarkupId()));

        header.add(title);
        header.add(closeButton("closeButton"));
        footer.add(actions("actions"));

        WebMarkupContainer dialog;
        add(dialog = dialog("dialog"));
        dialog.add(header, body, footer);

        add(new ModalCloseBehavior());
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

    protected Component title(String wicketId) {
        return new Label(wicketId, Model.of());
    }

    protected WebMarkupContainer dialog(String wicketId) {
        return new ModalDialog(wicketId);
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
        return new WebMarkupContainer(wicketId);
    }

    protected ListView<Component> actions(String wicketId) {
        return new Actions(wicketId, actions);
    }

    protected IModel<String> closeButtonLabel() {
        return new ResourceModel("close", "Close");
    }

    public Modal hideHeader(boolean hide) {
        header.setVisible(!hide);
        return this;
    }

    // TODO: Modal dialog size

    public Modal title(IModel<?> title) {
        this.title.setDefaultModel(title);
        return this;
    }

    public <T extends Component> Modal content(SerializableFunction<String, T> constructor) {
        Component component = constructor.apply("content");
        return content(component);
    }

    private Modal content(Component component) {
        component.setRenderBodyOnly(true);
        body.addOrReplace(component);
        return this;
    }

    public Modal show(IPartialPageRequestHandler target) {
        assertContent();
        showModal(target);
        appendShowDialogJavaScript(target);
        return this;
    }

    private void assertContent() {
        if (null == body.get("content")) {
            throw new WicketRuntimeException("Missing modal content. Use modal.content(...).show(...)");
        }
    }

    private void showModal(IPartialPageRequestHandler target) {
        setVisible(true);
        target.add(this);
    }

    public Modal close(IPartialPageRequestHandler target) {
        appendCloseDialogJavaScript(target);
        return this;
    }

    public Modal addCloseAction() {
        return addCloseAction(SerializableUnaryOperator.identity());
    }

    public Modal addCloseAction(SerializableUnaryOperator<CloseAction> customizer) {
        return addAction(id -> {
            ResourceModel label = new ResourceModel("close", "Close");
            CloseAction action = new CloseAction(id, label);
            customizer.apply(action);
            return action;
        });
    }

    public Modal addSubmitAction() {
        return addSubmitAction(SerializableUnaryOperator.identity());
    }

    public Modal addSubmitAction(SerializableUnaryOperator<SubmitAction> customizer) {
        Form<?> form = findForm().orElseThrow(() -> new IllegalStateException("Form cannot be found in the hierarchy"));
        return addAction(id -> {
            ResourceModel label = new ResourceModel("submit", "Submit");
            SubmitAction action = new SubmitAction(id, form, label);
            customizer.apply(action);
            return action;
        });
    }

    public Modal addAction(SerializableFunction<String, AbstractLink> constructor) {
        AbstractLink button = constructor.apply("action");
        return addAction(button);
    }

    private Modal addAction(AbstractLink action) {
        actions.add(action);
        return this;
    }

    private void appendShowDialogJavaScript(IPartialPageRequestHandler target) {
        target.appendJavaScript(createActionScript(getMarkupId(true), "show"));
    }

    private void appendCloseDialogJavaScript(IPartialPageRequestHandler target) {
        target.prependJavaScript(createActionScript(getMarkupId(true), "hide"));
    }

    private String createInitializationScript(String markupId) {
        return "new bootstrap.Modal(document.getElementById('" + markupId + "'));";
    }

    private String createActionScript(String markupId, String action) {
        return "bootstrap.Modal.getInstance(document.getElementById('" + markupId + "'))." + action + "();";
    }

    @SuppressWarnings("rawtypes")
    private Optional<Form> findForm() {
        return body.streamChildren()
            .filter(Form.class::isInstance)
            .map(Form.class::cast)
            .findFirst();
    }


    private static class ModalDialog extends TransparentWebMarkupContainer {

        public ModalDialog(String id) {
            super(id);
        }

        @Override
        protected void onComponentTag(ComponentTag tag) {
            super.onComponentTag(tag);

            // TODO: Dialog sizes
        }
    }

    private static class Actions extends ListView<Component> {

        public Actions(String id, List<Component> actions) {
            super(id, actions);
        }

        @Override
        protected void populateItem(ListItem<Component> item) {
            item.add(item.getModelObject());
        }
    }


    public class CloseAction extends AjaxLink<String> {

        public CloseAction(String id, IModel<String> label) {
            super(id, label);
        }

        @Override
        protected void onInitialize() {
            super.onInitialize();

            setBody(getDefaultModel());

            add(AttributeModifier.replace("type", "button"));
            add(AttributeModifier.replace("data-bs-dismiss", "modal"));
            add(AttributeModifier.append("class", "btn btn-secondary"));
        }

        @Override
        protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
            super.updateAjaxAttributes(attributes);

            String modalMarkupId = Modal.this.getMarkupId();
            AjaxCallListener listener = new AjaxCallListener();
            listener.onBeforeSend(createActionScript(modalMarkupId, "hide"));
            attributes.getAjaxCallListeners().add(listener);
        }

        @Override
        public void onClick(AjaxRequestTarget target) {
        }

        public CloseAction label(IModel<String> label) {
            setDefaultModel(label);
            return this;
        }

        // TODO: Close button size

        // TODO: Close button color
    }

    public class SubmitAction extends AjaxSubmitLink {

        private final IModel<String> label;

        public SubmitAction(String id, Form<?> form, IModel<String> label) {
            super(id, form);

            this.label = label;
        }

        @Override
        protected void onInitialize() {
            super.onInitialize();

            add(AttributeModifier.replace("type", "submit"));
            add(AttributeModifier.replace("form", getForm().getMarkupId(true)));
            add(AttributeModifier.append("class", "btn btn-primary"));

            setBody(label);
        }

        @Override
        protected void onAfterSubmit(AjaxRequestTarget target) {
            appendCloseDialogJavaScript(target);
        }

        public SubmitAction label(IModel<String> label) {
            setDefaultModel(label);
            return this;
        }

        // TODO: Close button size

        // TODO: Close button color
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
            actions.clear();
        }

        private void hideDialog(IPartialPageRequestHandler target) {
            Modal.this.setVisible(false);
            target.add(Modal.this);
        }
    }
}
