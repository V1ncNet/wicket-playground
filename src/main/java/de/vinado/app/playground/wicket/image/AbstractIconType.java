package de.vinado.app.playground.wicket.image;

public abstract class AbstractIconType implements IconType {

    @Override
    public String toCssClasses() {
        return style() + " " + name();
    }

    protected abstract String style();

    protected abstract String name();
}
