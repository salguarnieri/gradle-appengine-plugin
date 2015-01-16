package com.google.gcloud.exec

import com.google.gcloud.exec.io.StreamConsumer
import org.gradle.api.GradleException

class SynchronousProcessRunner extends ProcessRunner {

    final StreamConsumer streamConsumer
    final String[] command

    public SynchronousProcessRunner(String[] command, StreamConsumer streamConsumer) {
        this.command = command
        this.streamConsumer = streamConsumer
    }

    @Override
    public int run() throws GradleException {
        try {
            Process p = startProcess(command);
            streamConsumer.consumeStreams(p)
            p.waitFor();
            streamConsumer.stop()
            return p.exitValue();
        } catch (IOException e) {
            throw new GradleException("Build failed during gcloud execution", e);
        } catch (InterruptedException e) {
            throw new GradleException("Build failed during gcloud execution", e);
        }
    }
}
