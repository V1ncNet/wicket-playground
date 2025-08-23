package de.vinado.app.playground.dashboard.presentation.ui;

import de.vinado.app.playground.dashboard.support.MavenBuildTask;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
class DashboardBuildTask implements MavenBuildTask {

    @Override
    public String name() {
        return "dashboard";
    }

    @Override
    public void run() {
        File buildLog = null;
        try {
            ProcessBuilder pb = new ProcessBuilder(
                "mvn",
                "com.github.eirslett:frontend-maven-plugin:npm@npm-run-build",
                "resources:copy-resources@copy-react-build"
            );
            buildLog = File.createTempFile("build_", ".log");
            pb.redirectOutput(buildLog);
            Process start = pb.start();
            ExitCode exitCode = ExitCode.parseExitCode(start.waitFor());
            if (log.isDebugEnabled() || ExitCode.SUCCESS.compareTo(exitCode) < 0) {
                log.atLevel(exitCode.level()).log("Built React bundle with exit code {}", exitCode.value());
            }

            if (ExitCode.ERROR.equals(exitCode)) {
                print(buildLog);
            }
        } catch (IOException | InterruptedException e) {
            log.error("Failed to rebuild React application", e);
        } finally {
            if (null != buildLog) {
                buildLog.delete();
            }
        }
    }

    private static void print(File buildLog) {
        try {
            String output = FileUtils.readFileToString(buildLog, StandardCharsets.UTF_8);
            System.out.println(output);
        } catch (IOException e) {
            log.error("Failed to print build log {}", buildLog.getAbsolutePath(), e);
        }
    }
}
