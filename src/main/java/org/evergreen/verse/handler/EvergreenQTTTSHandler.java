package org.evergreen.verse.handler;

import org.evergreen.client.AmazonPollyProxy;
import org.evergreen.lambda.common.LambdaScratchSpace;
import org.evergreen.verse.Passages;

import java.io.File;
import java.io.InputStream;

public class EvergreenQTTTSHandler {

    private AmazonPollyProxy amazonPollyProxy;
    private EvergreenQTBucketHandler evergreenQTBucketHandler;

    public EvergreenQTTTSHandler(final AmazonPollyProxy amazonPollyProxy, final EvergreenQTBucketHandler evergreenQTBucketHandler) {
        this.amazonPollyProxy = amazonPollyProxy;
        this.evergreenQTBucketHandler = evergreenQTBucketHandler;
    }

    public String handle(final Passages passages) {
        final InputStream inputStream = amazonPollyProxy.synthesizeSpeech(passages.getTexts());
        final File targetFile = LambdaScratchSpace.createTemporaryFile(inputStream);
        final String s3PathString = evergreenQTBucketHandler.putObject(targetFile);

        return s3PathString;
    }
}
