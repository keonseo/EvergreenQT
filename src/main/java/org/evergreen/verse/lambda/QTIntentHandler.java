package org.evergreen.verse.lambda;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.interfaces.audioplayer.PlayBehavior;
import com.amazon.ask.request.Predicates;
import org.evergreen.verse.DateRequest;
import org.evergreen.verse.handler.EvergreenQTPassageResult;

import java.util.Optional;

public class QTIntentHandler implements RequestHandler {

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(Predicates.intentName("QT"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        final EvergreenQTPassageResult evergreenQTPassageResult = GetRegionLambdaFunction.handleRequest(new DateRequest().setYear("2018").setMonth("8").setDay("12"));

        return input.getResponseBuilder()
                .addAudioPlayerPlayDirective(PlayBehavior.REPLACE_ALL, 0L, null, "A", evergreenQTPassageResult.getS3PathToVoice())
                .withSimpleCard(AlexaSkillConstant.CARD_TITLE, evergreenQTPassageResult.getPassages().toString())
                .build();
    }
}
