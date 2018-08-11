package org.evergreen.client;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.polly.AmazonPolly;
import com.amazonaws.services.polly.AmazonPollyClientBuilder;
import com.amazonaws.services.polly.model.*;

import java.io.InputStream;

public class AmazonPollyProxy {
    private AmazonPolly amazonPolly;

    public AmazonPollyProxy(final Regions regions) {
        amazonPolly = AmazonPollyClientBuilder.standard()
                .withRegion(regions)
                .build();
    }

    public InputStream synthesizeSpeech(final String koreanText) {
        final SynthesizeSpeechResult result = amazonPolly.synthesizeSpeech(createSynthesizeSpeechRequest(koreanText));
        return result.getAudioStream();
    }

    public SynthesizeSpeechRequest createSynthesizeSpeechRequest(final String koreanText) {
        final SynthesizeSpeechRequest synthesizeSpeechRequest = new SynthesizeSpeechRequest()
                .withLanguageCode(LanguageCode.KoKR)
                .withOutputFormat(OutputFormat.Mp3)
                .withVoiceId(VoiceId.Seoyeon)
                .withText(koreanText);

        return synthesizeSpeechRequest;
    }
}
