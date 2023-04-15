package de.vinado.app.playground;

import org.springframework.web.context.request.RequestContextListener;

import javax.servlet.annotation.WebListener;

@WebListener
public class SessionAwareRequestContextListener extends RequestContextListener {
}
