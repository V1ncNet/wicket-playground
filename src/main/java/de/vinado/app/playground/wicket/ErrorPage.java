package de.vinado.app.playground.wicket;

import de.vinado.app.playground.wicket.bootstrap.BootstrapPage;

public abstract class ErrorPage extends BootstrapPage {

    protected ErrorPage() {
        super();
    }

    @Override
    public boolean isErrorPage() {
        return true;
    }

    @Override
    public boolean isVersioned() {
        return false;
    }
}
