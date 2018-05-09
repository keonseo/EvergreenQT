package org.evergreen.lambda.testing;

import org.evergreen.lambda.common.LambaEnvironmentVariables;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class GetRegionLambdaFunction implements RequestHandler<TestRequest, String> {

    private LambdaLogger logger;

    @Override
    public String handleRequest(final TestRequest request, final Context context) {

        logger = context.getLogger();
        logger.log("Test logging with region: " + request.getInput());

        return LambaEnvironmentVariables.getRegion().getName();
    }
}
