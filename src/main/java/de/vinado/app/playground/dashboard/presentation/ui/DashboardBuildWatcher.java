package de.vinado.app.playground.dashboard.presentation.ui;

import de.vinado.app.playground.dashboard.support.FileChangeListener;
import de.vinado.app.playground.dashboard.support.FileWatcherSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchService;
import java.util.concurrent.ExecutorService;

@Configuration
class DashboardBuildWatcher extends FileWatcherSupport {

    public static final Path SOURCE_DIR = Paths.get("apps/dashboard/src/").toAbsolutePath();

    public DashboardBuildWatcher() throws IOException {
    }

    @Bean
    public FileChangeListener dashboardFileChangeListener() {
        WatchService watcher = getWatcher();
        ExecutorService executor = getExecutor();
        DashboardBuildTask action = new DashboardBuildTask();
        executor.execute(action);
        return new FileChangeListener(SOURCE_DIR, watcher, executor, action);
    }
}
