package de.vinado.app.playground.security.web.oauth2;

import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Supplier;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthenticationResolver {

    private final AuthenticationHolder authentication;

    public AuthenticationResolver() {
        this(() -> SecurityContextHolder.getContext().getAuthentication());
    }

    public Optional<AuthenticatedPrincipal> getAuthenticatedPrincipal() {
        return convertFromOauth2Login();
    }

    private Optional<AuthenticatedPrincipal> convertFromOauth2Login() {
        return getAuthentication()
            .map(Authentication::getPrincipal)
            .filter(DefaultOidcUser.class::isInstance)
            .map(DefaultOidcUser.class::cast);
    }

    private Optional<Authentication> getAuthentication() {
        return Optional.ofNullable(authentication.get());
    }


    @FunctionalInterface
    public interface AuthenticationHolder extends Supplier<Authentication> {
    }
}
