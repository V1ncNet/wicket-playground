package de.vinado.app.playground.wicket.navigation;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.apache.wicket.Page;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.io.Serializable;

@Value
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class NavigationItem implements Serializable {

    Class<? extends Page> page;
    Serializable label;
    PageParameters parameters;
    boolean enabled;

    public static Builder builder(Class<? extends Page> page, Serializable label) {
        return new Builder(page, label);
    }


    @RequiredArgsConstructor
    public static class Builder {

        private final Class<? extends Page> page;
        private final Serializable label;
        private PageParameters parameters = new PageParameters();
        private boolean enabled = true;

        public Builder parameters(PageParameters parameters) {
            this.parameters = parameters;
            return this;
        }

        public Builder enabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public NavigationItem build() {
            return new NavigationItem(page, label, parameters, enabled);
        }
    }
}
