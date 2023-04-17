package de.vinado.app.playground.document.presentation.ui;

import java.net.MalformedURLException;
import java.net.URL;

@FunctionalInterface
public interface PreviewUrlProvider {

    URL resolve(ViewableDocument document) throws MalformedURLException;
}
