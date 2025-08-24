package de.vinado.app.playground.dashboard.presentation.api.v1;

import de.vinado.app.playground.security.web.oauth2.AuthenticationResolver;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(DashboardRestController.PATH)
@RequiredArgsConstructor
class DashboardRestController {

    public static final String PATH = "/api/v1/dashboard";

    private final AuthenticationResolver authenticationResolver;

    @GetMapping(path = "/greet", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> greet() {
        AuthenticatedPrincipal principal = authenticationResolver.getAuthenticatedPrincipal()
            .orElse(() -> "anonymous");
        return ResponseEntity.ok("Hello, " + principal.getName() + "!");
    }
}
