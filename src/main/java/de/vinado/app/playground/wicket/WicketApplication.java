package de.vinado.app.playground.wicket;

import org.apache.wicket.Page;
import org.apache.wicket.mock.MockHomePage;
import org.apache.wicket.protocol.http.WebApplication;
import org.springframework.stereotype.Component;

@Component
public class WicketApplication extends WebApplication {

    @Override
    public Class<? extends Page> getHomePage() {
        return MockHomePage.class;
    }

    @Override
    protected void init() {
        super.init();

        mountPages();
    }

    private void mountPages() {
    }
}
