package de.vinado.app.playground.wicket.test;

import de.vinado.app.playground.security.web.oauth2.AuthenticationResolver;
import de.vinado.app.playground.wicket.WicketApplication;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.tester.WicketTestCase;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ActiveProfiles("wicket")
@SpringBootTest(classes = WicketApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public abstract class SpringEnabledWicketTestCase extends WicketTestCase {

    @MockitoBean
    private Environment environment;

    @MockitoBean
    private AuthenticationResolver authenticationResolver;

    @Autowired
    private WebApplication webApplication;

    @BeforeEach
    void setUp() {
        when(environment.matchesProfiles("oauth2")).thenReturn(false);
        when(authenticationResolver.getAuthenticatedPrincipal()).thenReturn(Optional.empty());
    }

    @Override
    protected WebApplication newApplication() {
        return webApplication;
    }
}
