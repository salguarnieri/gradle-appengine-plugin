package com.google.gcloud.exec

import com.google.gcloud.exec.io.StreamConsumer

/**
 * Created by appu on 2/11/15.
 */
abstract class ProcessRunner {

    File workingDir

    public abstract int run()

    protected Process startProcess(String[] command) throws IOException {
        ProcessBuilder pb = new ProcessBuilder(command)
        if (workingDir) {
            pb.directory(workingDir)
        }
        pb.redirectInput()
        pb.redirectErrorStream(true)

        final Process gcloudProcess = pb.start()

        Runtime.getRuntime().addShutdownHook(new Thread() {
            //TODO : Figure out if sending TERM signal will work
            @Override
            public void run() {
                if (gcloudProcess != null) {
                    gcloudProcess.destroy()
                }
            }
        });
        return gcloudProcess
    }
}
