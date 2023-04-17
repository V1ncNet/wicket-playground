package de.vinado.app.playground.document.presentation.ui;

import org.apache.wicket.csp.CSPDirective;
import org.apache.wicket.csp.ContentSecurityPolicySettings;
import org.apache.wicket.protocol.http.WebApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("wicket")
@Configuration
public class PreviewConfiguration {

    @Autowired
    public void init(WebApplication webApplication) {
        ContentSecurityPolicySettings cspSettings = webApplication.getCspSettings();
        configure(cspSettings);
    }

    private void configure(ContentSecurityPolicySettings settings) {
        settings.blocking()
            .add(CSPDirective.FRAME_SRC, "*");
    }
}
