package de.vinado.app.playground.wicket.bootstrap.toast;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes.EventPropagation;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;

import java.io.Serializable;

public class Toast extends GenericPanel<Serializable> {

    public Toast(String id, IModel<Serializable> model) {
        super(id, model);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

//        setOutputMarkupPlaceholderTag(true);
//        setVisible(false);

        add(body("body"));
        add(closeButton("closeButton"));

        add(new HiddenEventBehavior());
    }

    @Override
    protected void onComponentTag(ComponentTag tag) {
        super.onComponentTag(tag);

        tag.put("class", "toast align-items-center");
        tag.put("role", "alert");
        tag.put("aria-live", "assertive");
        tag.put("aria-atomic", "true");
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);

        String markupId = getMarkupId(true);
        String initializationScript = createInitializationScript(markupId)
            + "\n"
            + createActionScript(markupId, "show");
        response.render(OnDomReadyHeaderItem.forScript(initializationScript));
    }

    protected WebMarkupContainer body(String wicketId) {
        return new WebMarkupContainer(wicketId, getModel()) {

            @Override
            public void onComponentTagBody(MarkupStream markupStream, ComponentTag openTag) {
                replaceComponentTagBody(markupStream, openTag, getModelObject().toString());
            }
        };
    }

    protected Component closeButton(String wicketId) {
        return new CloseButton(wicketId);
    }

    public Toast show(AjaxRequestTarget target) {
        showToast(target);
        appendShowDialogJavaScript(target);
        return this;
    }

    private void showToast(AjaxRequestTarget target) {
        setVisible(true);
        target.add(this);
    }

    public Toast hide(AjaxRequestTarget target) {
        appendHideDialogJavaScript(target);
        return this;
    }

    protected void appendShowDialogJavaScript(AjaxRequestTarget target) {
        target.appendJavaScript(createActionScript(getMarkupId(true), "show"));
    }

    protected void appendHideDialogJavaScript(AjaxRequestTarget target) {
        target.prependJavaScript(createActionScript(getMarkupId(true), "hide"));
    }

    protected static String createInitializationScript(String markupId) {
        return "new bootstrap.Toast(document.getElementById('" + markupId + "'));";
    }

    protected static String createActionScript(String markupId, String action) {
        return "bootstrap.Toast.getInstance(document.getElementById('" + markupId + "'))." + action + "();";
    }

    public static class CloseButton extends AjaxLink<Void> {

        public CloseButton(String id) {
            super(id);
        }

        @Override
        protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
            super.updateAjaxAttributes(attributes);

            Toast toast = findParent(Toast.class);
            String toastMarkupId = toast.getMarkupId();
            AjaxCallListener listener = new AjaxCallListener();
            listener.onBeforeSend(createActionScript(toastMarkupId, "hide"));
            attributes.getAjaxCallListeners().add(listener);
        }

        @Override
        public void onClick(AjaxRequestTarget target) {
        }
    }

    private class HiddenEventBehavior extends AjaxEventBehavior {

        public HiddenEventBehavior() {
            super("hidden.bs.toast");
        }

        @Override
        protected void onEvent(AjaxRequestTarget target) {
            if (isVisibleInHierarchy()) {
                handleHiddenEvent(target);
            }
        }

        @Override
        protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
            super.updateAjaxAttributes(attributes);

            attributes.setEventPropagation(EventPropagation.BUBBLE);
        }

        private void handleHiddenEvent(AjaxRequestTarget target) {
            disposeToast(target);
        }

        private void disposeToast(AjaxRequestTarget target) {
            Toast toast = Toast.this;
            toast.setVisible(false);
            target.add(toast);
        }
    }
}
