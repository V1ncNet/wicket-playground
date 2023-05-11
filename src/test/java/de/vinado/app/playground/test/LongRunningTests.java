package de.vinado.app.playground.test;

import org.junit.jupiter.api.Tag;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Tag("de.vinado.app.playground.test.LongRunningTests")
public @interface LongRunningTests {
}
