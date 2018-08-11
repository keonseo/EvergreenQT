package org.evergreen.client;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.polly.model.SynthesizeSpeechRequest;
import com.amazonaws.services.polly.model.VoiceId;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AmazonPollyProxyTest {

    @Test
    public void isAbleToCreateClient() {
        new AmazonPollyProxy(Regions.US_EAST_1);
    }

    @Test
    public void createSynthesizeSpeechRequestInKorean() {
        final AmazonPollyProxy pollyProxy = new AmazonPollyProxy(Regions.US_EAST_1);

        final String koreanText = "하이";
        final SynthesizeSpeechRequest request = pollyProxy.createSynthesizeSpeechRequest(koreanText);

        assertEquals(koreanText, request.getText());
        assertEquals("ko-KR", request.getLanguageCode());
        assertEquals("mp3", request.getOutputFormat());
        assertEquals(VoiceId.Seoyeon.name(), request.getVoiceId());
    }
}
