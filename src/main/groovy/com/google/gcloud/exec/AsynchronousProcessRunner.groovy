package com.google.gcloud.exec

import com.google.gcloud.exec.io.StreamConsumer

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class AsynchronousProcessRunner extends ProcessRunner {

    final ExecutorService executorService
    final StreamConsumer streamConsumer
    final String[] command
    final Callback callback

    public AsynchronousProcessRunner(String[] command, StreamConsumer streamConsumer, Callback callback) {
        this.command = command
        this.streamConsumer = streamConsumer
        this.callback = callback
        executorService = Executors.newSingleThreadExecutor()
    }

    @Override
    public int run() {
        executorService.submit(new Runnable() {
            @Override
            void run() {
                try {
                    Process p = startProcess(command);
                    streamConsumer.consumeStreams(p)
                    p.waitFor();
                    if (callback != null) {
                        callback.onCompleted(p.exitValue());
                    }
                } catch (Exception e) {
                    if (callback != null) {
                        callback.onFailedWithException(e);
                    }
                }
                streamConsumer.stop()
            }
        })
        return 0
    }

    public interface Callback {
        public void onCompleted(int exitCode);
        public void onFailedWithException(Exception ex);
    }
}
