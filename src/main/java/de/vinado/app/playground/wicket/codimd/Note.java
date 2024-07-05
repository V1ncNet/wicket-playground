package de.vinado.app.playground.wicket.codimd;

import java.io.Serializable;
import java.util.UUID;

import lombok.NonNull;
import lombok.Value;
import lombok.experimental.Accessors;

@Value
@Accessors(fluent = false)
public class Note implements Serializable {

    @NonNull
    UUID id;
}
