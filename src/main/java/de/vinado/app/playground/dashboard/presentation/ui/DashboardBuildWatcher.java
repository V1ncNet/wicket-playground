package de.vinado.app.playground.dashboard.presentation.ui;

import de.vinado.app.playground.dashboard.support.FileChangeListener;
import de.vinado.app.playground.dashboard.support.FileWatcherSupport;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.WatchService;
import java.util.concurrent.ExecutorService;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Configuration
@EnableConfigurationProperties(DashboardBuildWatcher.Properties.class)
@ConditionalOnProperty(value = "app.dashboard.react.watch.enabled", havingValue = "true")
class DashboardBuildWatcher extends FileWatcherSupport {

    private final Properties properties;

    public DashboardBuildWatcher(Properties properties) throws IOException {
        this.properties = properties;
    }

    @Bean
    public FileChangeListener dashboardFileChangeListener() {
        Path path = properties.getPath().toAbsolutePath();
        WatchService watcher = getWatcher();
        ExecutorService executor = getExecutor();
        DashboardBuildTask action = new DashboardBuildTask();
        executor.execute(action);
        return new FileChangeListener(path, watcher, executor, action);
    }


    @ConfigurationProperties("app.dashboard.react.watch")
    @Value
    @RequiredArgsConstructor(onConstructor_ = {@ConstructorBinding})
    public static class Properties {

        boolean enabled;
        Path path;
    }
}
