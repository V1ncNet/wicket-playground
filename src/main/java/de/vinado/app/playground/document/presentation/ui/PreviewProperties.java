package de.vinado.app.playground.document.presentation.ui;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URL;

@Getter
@Setter
@ConfigurationProperties("app.document.preview")
public class PreviewProperties {

    private URL baseUrl;
}
