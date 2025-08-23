package de.vinado.app.playground.dashboard.support;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.WatchService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class FileWatcherSupport implements Closeable {

    private final WatchService watcher;
    private final ExecutorService executor;

    public FileWatcherSupport() throws IOException {
        this.watcher = FileSystems.getDefault().newWatchService();
        this.executor = Executors.newFixedThreadPool(1);
    }

    @Override
    public void close() throws IOException {
        log.info("Shutting down file watcher");
        watcher.close();
        executor.shutdown();
        log.info("Shutdown complete");
    }
}
