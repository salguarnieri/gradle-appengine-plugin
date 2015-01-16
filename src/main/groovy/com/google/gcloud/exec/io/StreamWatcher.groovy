package com.google.gcloud.exec.io

trait StreamWatcher {

    StreamInspector inspector

    void setInspector(StreamInspector inspector) {
        this.inspector = inspector
    }

    public static interface StreamInspector {
        void inspectLine(String line)
    }
}