package de.vinado.app.playground.dashboard.support;

import org.slf4j.event.Level;

import java.util.Arrays;
import java.util.function.Predicate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

public interface MavenBuildTask extends Runnable {

    String name();


    @RequiredArgsConstructor
    @Accessors(fluent = true)
    @Getter
    enum ExitCode {

        SUCCESS(0, Level.INFO),
        ERROR(1, Level.ERROR),
        CANCELLED(137, Level.WARN);

        private final int value;
        private final Level level;

        public static ExitCode parseExitCode(int source) {
            return Arrays.stream(values())
                .filter(by(source))
                .findFirst()
                .orElse(ERROR);
        }

        private static Predicate<ExitCode> by(int source) {
            return exitCode -> exitCode.value() == source;
        }
    }
}
