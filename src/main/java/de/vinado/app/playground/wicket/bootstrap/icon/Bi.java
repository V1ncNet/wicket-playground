package de.vinado.app.playground.wicket.bootstrap.icon;

import de.vinado.app.playground.wicket.image.AbstractIconType;
import de.vinado.app.playground.wicket.image.IconType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Bi extends AbstractIconType {

    public static final IconType FILE = new Bi("file");
    public static final IconType HOUSE = new Bi("house");

    @Getter(AccessLevel.PROTECTED)
    private final String style = "bi";
    private final String name;

    @Override
    protected String getName() {
        return "bi-" + name;
    }
}