package de.vinado.app.playground.wicket.behavior;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.request.cycle.RequestCycle;

import java.util.function.Consumer;

public class UpdateOnEventBehavior<T> extends OnEventBehavior<T> {

    public UpdateOnEventBehavior(Class<T> type) {
        super(type);
    }

    @Override
    protected void onEvent(Component component, T payload) {
        if (ready(component)) {
            onBeforeUpdate(payload);
            registerUpdate(component);
            onAfterUpdate(payload);
        }
    }

    protected boolean ready(Component component) {
        return component.isVisibleInHierarchy();
    }

    protected void onBeforeUpdate(T payload) {
    }

    private static void registerUpdate(Component component) {
        update(target -> target.add(component));
    }

    private static void update(Consumer<AjaxRequestTarget> callback) {
        RequestCycle.get().find(AjaxRequestTarget.class).ifPresent(callback);
    }

    protected void onAfterUpdate(T payload) {
    }
}
