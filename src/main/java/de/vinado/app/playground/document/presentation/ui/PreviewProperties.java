package de.vinado.app.playground.document.presentation.ui;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URL;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties("app.document.preview")
public class PreviewProperties {

    private URL baseUrl;
}
