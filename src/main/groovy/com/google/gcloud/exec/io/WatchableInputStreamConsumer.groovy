package com.google.gcloud.exec.io

import org.codehaus.groovy.runtime.IOGroovyMethods
import org.gradle.api.logging.LogLevel
import org.gradle.api.logging.Logger

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class WatchableInputStreamConsumer implements StreamConsumer, StreamWatcher {

    final ExecutorService executorService
    final Logger logger
    final LogLevel logLevel

    WatchableInputStreamConsumer(Logger logger, LogLevel logLevel) {
        executorService = Executors.newSingleThreadExecutor()
        this.logger = logger
        this.logLevel = logLevel
    }

    @Override
    void consumeStreams(Process process) {
        if (!process.getErrorStream()) {
           throw new IllegalStateException("Process error stream must be redirected to input stream")
        }

        executorService.submit(new Runnable() {
            @Override
            void run() {
                try {
                    InputStream streamReader = process.getInputStream()
                    IOGroovyMethods.withReader(streamReader) {
                        streamReader.eachLine { String line ->
                            if (Thread.currentThread().interrupted) {
                                throw new InterruptedException()
                            }
                            inspector?.inspectLine(line)
                            logger.log(logLevel ?: LogLevel.LIFECYCLE, line)
                        }
                    }
                } catch (InterruptedException e) {
                    // do nothing, executor probably killed us
                }
            }
        })
    }

    @Override
    void stop() {
        executorService.shutdownNow()
    }
}
