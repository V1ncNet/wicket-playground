package de.vinado.app.playground.wicket;

import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.request.Request;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class WicketSession extends AbstractAuthenticatedWebSession {

    public WicketSession(Request request) {
        super(request);

        Injector.get().inject(this);
    }

    @Override
    public Roles getRoles() {
        if (!isSignedIn()) {
            return new Roles();
        }

        Authentication authentication = authentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return authorities.stream()
            .map(GrantedAuthority::getAuthority)
            .filter(byPrefix("ROLE_"))
            .map(WicketSession::leadingRoleAbsent)
            .map(String::toUpperCase)
            .collect(Collectors.collectingAndThen(Collectors.joining(","), Roles::new));
    }

    @Override
    public boolean isSignedIn() {
        Authentication authentication = authentication();
        return authentication.isAuthenticated();
    }

    private Authentication authentication() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return securityContext.getAuthentication();
    }

    private static String leadingRoleAbsent(String authority) {
        return authority.replaceFirst("^ROLE_", "");
    }

    private static Predicate<String> byPrefix(String prefix) {
        return self -> self.startsWith(prefix);
    }
}
