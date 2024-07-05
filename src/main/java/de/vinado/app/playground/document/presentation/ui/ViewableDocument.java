package de.vinado.app.playground.document.presentation.ui;

import java.io.Serializable;
import java.net.URI;

import lombok.Value;

@Value
public class ViewableDocument implements Serializable {

    URI uri;
}
