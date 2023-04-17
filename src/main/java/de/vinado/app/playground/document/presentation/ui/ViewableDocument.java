package de.vinado.app.playground.document.presentation.ui;

import lombok.Value;

import java.io.Serializable;
import java.net.URI;

@Value
public class ViewableDocument implements Serializable {

    URI uri;
}
