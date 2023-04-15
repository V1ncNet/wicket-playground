package de.vinado.app.playground.wicket.image;

import java.io.Serializable;

@FunctionalInterface
public interface IconType extends Serializable {

    String toCssClasses();
}
