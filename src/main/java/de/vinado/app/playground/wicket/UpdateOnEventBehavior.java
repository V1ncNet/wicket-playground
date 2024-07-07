package de.vinado.app.playground.wicket;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.request.cycle.RequestCycle;

import java.util.Optional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdateOnEventBehavior extends Behavior {

    private final Class<?> type;

    @Override
    public void bind(Component component) {
        component.setOutputMarkupId(true);
    }

    @Override
    public void onEvent(Component component, IEvent<?> event) {
        Optional.ofNullable(event.getPayload())
            .filter(type::isInstance)
            .map(type::cast)
            .flatMap(__ -> RequestCycle.get().find(AjaxRequestTarget.class))
            .ifPresent(target -> target.add(component));
    }
}
