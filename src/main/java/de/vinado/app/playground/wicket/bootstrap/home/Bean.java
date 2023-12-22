package de.vinado.app.playground.wicket.bootstrap.home;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.Period;

@Data
public class Bean implements Serializable {

    private Range range;
    private LocalDateTime start;
    private LocalDateTime end;
}
