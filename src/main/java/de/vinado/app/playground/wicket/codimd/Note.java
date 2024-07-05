package de.vinado.app.playground.wicket.codimd;

import lombok.NonNull;
import lombok.Value;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.UUID;

@Value
@Accessors(fluent = false)
public class Note implements Serializable {

    @NonNull
    UUID id;
}
