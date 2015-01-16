package com.google.gcloud.exec.io

interface StreamConsumer {
    void consumeStreams(Process process)
    void stop()
}
