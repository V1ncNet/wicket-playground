package de.vinado.app.playground.note.presentation.ui;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("app.note")
public class NoteProperties {
}
