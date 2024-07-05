package de.vinado.app.playground.note.presentation.ui;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URL;

@Getter
@Setter
@Accessors(fluent = false)
@ConfigurationProperties("app.note")
public class NoteProperties {

    private Codimd codimd = new Codimd();


    @Getter
    @Setter
    @Accessors(fluent = false)
    public static class Codimd {

        private URL baseUrl;
    }
}
