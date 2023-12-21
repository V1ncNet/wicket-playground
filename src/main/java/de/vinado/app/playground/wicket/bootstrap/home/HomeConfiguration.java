package de.vinado.app.playground.wicket.bootstrap.home;

import de.vinado.app.playground.wicket.configuration.WicketConfigurer;
import org.apache.wicket.protocol.http.WebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("wicket & bootstrap")
@Configuration
public class HomeConfiguration implements WicketConfigurer {

    private static final String PATH = "/";

    @Override
    public void init(WebApplication webApplication) {
        mountPages(webApplication);
    }

    private void mountPages(WebApplication webApplication) {
        webApplication.mountPage(PATH, HomePage.class);
    }
}
