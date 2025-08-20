package de.vinado.app.playground.wicket.image;

public abstract class AbstractIconType implements IconType {

    @Override
    public String toCssClasses() {
        return getStyle() + " " + getName();
    }

    protected abstract String getStyle();

    protected abstract String getName();
}
