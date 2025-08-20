package de.vinado.app.playground.dashboard.presentation.api.v1;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(DashboardRestController.PATH)
class DashboardRestController {

    public static final String PATH = "/api/v1/dashboard";

    @GetMapping(path = "/greet", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> greet() {
        return ResponseEntity.ok("Hello, World!");
    }
}
