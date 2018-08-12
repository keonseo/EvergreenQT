package org.evergreen.verse.lambda;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.Predicates;

import java.util.Optional;

public class LaunchRequestHandler implements RequestHandler {

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(Predicates.requestType(LaunchRequest.class));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        final String speechText = "Welcome to Evergreen QT Skills. Please visit www.evergreenpromising.org for our church information";
        return input.getResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard(AlexaSkillConstant.CARD_TITLE, speechText)
                .withReprompt(speechText)
                .build();
    }
}
