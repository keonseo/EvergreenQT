package org.evergreen.verse.handler;

import org.evergreen.verse.Passages;

public class EvergreenQTPassageResult {

    private Passages passages;

    public Passages getPassages() {
        return passages;
    }

    public EvergreenQTPassageResult setPassages(final Passages passages) {
        this.passages = passages;
        return this;
    }

    public String getS3PathToVoice() {
        return s3PathToVoice;
    }

    public EvergreenQTPassageResult setS3PathToVoice(final String s3PathToVoice) {
        this.s3PathToVoice = s3PathToVoice;
        return this;
    }

    private String s3PathToVoice;

    @Override
    public String toString() {
        return "Passages: " + passages + " S3PathToVoice: " + s3PathToVoice;
    }
}
