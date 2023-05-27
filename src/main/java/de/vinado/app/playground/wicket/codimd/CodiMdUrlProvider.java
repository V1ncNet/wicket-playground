package de.vinado.app.playground.wicket.codimd;

import java.net.MalformedURLException;
import java.net.URL;

@FunctionalInterface
public interface CodiMdUrlProvider {

    URL resolve(Note note) throws MalformedURLException;
}
