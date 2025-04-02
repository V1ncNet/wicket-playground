package de.vinado.app.playground.wicket;

import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.request.Request;

public class WicketSession extends AbstractAuthenticatedWebSession {

    public WicketSession(Request request) {
        super(request);

        Injector.get().inject(this);
    }

    @Override
    public Roles getRoles() {
        return new Roles();
    }

    @Override
    public boolean isSignedIn() {
        return true;
    }
}
