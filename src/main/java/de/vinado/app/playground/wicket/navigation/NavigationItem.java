package de.vinado.app.playground.wicket.navigation;

import lombok.Value;
import org.apache.wicket.Page;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.io.Serializable;

@Value
public class NavigationItem implements Serializable {

    Class<? extends Page> page;
    Serializable label;
    PageParameters parameters;
    boolean enabled;
}
