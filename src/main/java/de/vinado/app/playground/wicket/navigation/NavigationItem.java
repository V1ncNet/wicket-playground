package de.vinado.app.playground.wicket.navigation;

import de.vinado.app.playground.wicket.image.IconType;
import org.apache.wicket.Page;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.io.Serializable;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class NavigationItem implements Serializable {

    Class<? extends Page> page;
    IconType icon;
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
        private IconType icon;
        private PageParameters parameters = new PageParameters();
        private boolean enabled = true;

        public Builder icon(IconType icon) {
            this.icon = icon;
            return this;
        }

        public Builder parameters(PageParameters parameters) {
            this.parameters = parameters;
            return this;
        }

        public Builder enabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public NavigationItem build() {
            return new NavigationItem(page, icon, label, parameters, enabled);
        }
    }
}
