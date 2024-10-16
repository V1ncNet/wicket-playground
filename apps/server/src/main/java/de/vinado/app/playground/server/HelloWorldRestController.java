package de.vinado.app.playground.server;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;

@RestController
@RequestMapping(HelloWorldRestController.PATH)
public class HelloWorldRestController {

    public static final String PATH = "/api/v1/hello-world";

    @GetMapping
    public ResponseEntity<HelloWorldResponseBody> helloWorld() {
        HelloWorldResponseBody body = new HelloWorldResponseBody();
        return ResponseEntity.ok(body);
    }


    @Data
    public static class HelloWorldResponseBody {

        private String message = "Hello, World!";
    }
}
