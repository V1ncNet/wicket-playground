package de.vinado.app.playground.wicket;

import de.vinado.app.playground.security.web.oauth2.AuthenticationResolver;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class WicketSession extends AbstractAuthenticatedWebSession {

    private final Environment environment;
    private final AuthenticationResolver authenticationResolver;

    public WicketSession(Request request, Environment environment, AuthenticationResolver authenticationResolver) {
        super(request);

        this.environment = environment;
        this.authenticationResolver = authenticationResolver;
    }

    @Override
    public Roles getRoles() {
        if (!isSignedIn()) {
            return new Roles();
        } else if (!environment.matchesProfiles("oauth2")) {
            return new AlwaysAuthorizedRoles();
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
        return !environment.matchesProfiles("oauth2")
            || authenticationResolver.getAuthenticatedPrincipal().isPresent();
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


    private static class AlwaysAuthorizedRoles extends Roles {

        @Override
        public boolean hasRole(String role) {
            return true;
        }

        @Override
        public boolean hasAnyRole(Roles roles) {
            return true;
        }

        @Override
        public boolean hasAllRoles(Roles roles) {
            return true;
        }

        @Override
        public String toString() {
            return "*";
        }
    }
}
