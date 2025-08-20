package de.vinado.app.playground.wicket.codimd;

import java.io.Serializable;
import java.util.UUID;

import lombok.NonNull;
import lombok.Value;

@Value
public class Note implements Serializable {

    @NonNull
    UUID id;
}
