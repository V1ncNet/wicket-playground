package de.vinado.app.playground.dashboard.support;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;

import java.io.IOException;
import java.nio.file.ClosedWatchServiceException;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import java.util.function.Predicate;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class FileChangeListener {

    private static final WatchEvent.Kind<?>[] EVENTS = new WatchEvent.Kind[]{
        StandardWatchEventKinds.ENTRY_CREATE,
        StandardWatchEventKinds.ENTRY_DELETE,
        StandardWatchEventKinds.ENTRY_MODIFY
    };

    @NonNull
    private final Path path;

    @NonNull
    private final WatchService watcher;

    @NonNull
    private final ExecutorService executor;

    @NonNull
    private final MavenBuildTask buildTask;

    @EventListener(ApplicationStartedEvent.class)
    public void watch() throws IOException {
        log.info("Registering {} change listener on {}", buildTask.name(), path);
        path.register(watcher, EVENTS);
        executor.execute(new BlockingWatcherTask(watcher, buildTask));
    }


    @RequiredArgsConstructor
    private static class BlockingWatcherTask implements Runnable {

        private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM);

        private final WatchService watcher;
        private final MavenBuildTask buildTask;

        @Override
        public void run() {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    WatchKey key = watcher.take();
                    List<WatchEvent<?>> events = key.pollEvents();
                    if (!events.isEmpty()) {
                        buildTask.run();
                    }
                    log(events);
                    key.reset();
                }
            } catch (InterruptedException | ClosedWatchServiceException e) {
                Thread.currentThread().interrupt();
            }
        }

        private void log(List<WatchEvent<?>> events) {
            events.stream()
                .filter(Predicate.not(ofKind(StandardWatchEventKinds.ENTRY_DELETE)))
                .map(WatchEvent::context)
                .filter(Path.class::isInstance)
                .map(Path.class::cast)
                .forEach(logAt(Instant.now()));
        }

        private Predicate<WatchEvent<?>> ofKind(WatchEvent.Kind<?>... kind) {
            List<WatchEvent.Kind<?>> list = List.of(kind);
            return event -> list.contains(event.kind());
        }

        private Consumer<Path> logAt(Instant timestamp) {
            return path -> {
                String time = timestamp.atZone(ZoneId.systemDefault()).format(FORMATTER);
                System.out.printf("\033[2;37m%s \033[1;36m[%s] \033[0;90m(%s) \033[0;32m%s \033[2;37m%s\033[0m%n",
                    time, "maven", buildTask.name(), "changed", path);
            };
        }
    }
}
