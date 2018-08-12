package org.evergreen.verse.handler;

import org.evergreen.client.IBiblesNetProxy;
import org.evergreen.verse.Passages;
import org.evergreen.verse.VerseFindRequest;

import java.util.List;
import java.util.Optional;

public class EvergreenQTPassageHandler {

    final EvergreenQTTableHandler evergreenQTTableHandler;
    final EvergreenQTTTSHandler evergreenQTTTSHandler;
    final IBiblesNetProxy iBiblesNetProxy;

    public EvergreenQTPassageHandler(final EvergreenQTTableHandler evergreenQTTableHandler,
                                     final EvergreenQTTTSHandler evergreenQTTTSHandler,
                                     final IBiblesNetProxy iBiblesNetProxy) {
        this.evergreenQTTableHandler = evergreenQTTableHandler;
        this.evergreenQTTTSHandler = evergreenQTTTSHandler;
        this.iBiblesNetProxy = iBiblesNetProxy;
    }

    public EvergreenQTPassageResult handle() {
        final VerseFindRequest verseFindRequest = evergreenQTTableHandler.generateVerseFindRequestFromDB();
        final Passages passages;

        final Optional<List<String>> textFromDBOptional = evergreenQTTableHandler.getTextFromDB();

        if (textFromDBOptional.isPresent()) {
            passages = new Passages.PassagesBuilder()
                    .withVerseFindRequest(verseFindRequest)
                    .withTexts(textFromDBOptional.get())
                    .build();
        } else {
            final List<String> textFromNet = iBiblesNetProxy.getText(verseFindRequest);
            evergreenQTTableHandler.putText(textFromNet);
            passages = new Passages.PassagesBuilder()
                    .withVerseFindRequest(verseFindRequest)
                    .withTexts(textFromNet)
                    .build();
        }

        // Check if we already processed this request
        final Optional<String> s3ObjectKeyOptional = evergreenQTTableHandler.getS3ObjectKey();
        final String s3ObjectKeyResult;

        if (s3ObjectKeyOptional.isPresent()) {
            s3ObjectKeyResult = s3ObjectKeyOptional.get();
        } else {
            s3ObjectKeyResult = processTTS(passages);
            evergreenQTTableHandler.putS3ObjectKey(s3ObjectKeyResult);
        }

        return new EvergreenQTPassageResult()
                .setPassages(passages)
                .setS3PathToVoice(s3ObjectKeyResult);
    }

    private String processTTS(final Passages passages) {
        return evergreenQTTTSHandler.handle(passages);
    }
}
