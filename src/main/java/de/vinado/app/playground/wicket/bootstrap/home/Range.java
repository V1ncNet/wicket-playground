package de.vinado.app.playground.wicket.bootstrap.home;

import java.io.Serializable;
import java.time.LocalDateTime;

public record Range(LocalDateTime start, LocalDateTime end) implements Serializable {
}
