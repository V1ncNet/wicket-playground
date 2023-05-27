package de.vinado.app.playground.wicket.codimd;

import lombok.NonNull;
import lombok.Value;

import java.io.Serializable;
import java.util.UUID;

@Value
public class Note implements Serializable {

    @NonNull
    UUID id;
}
