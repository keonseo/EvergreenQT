package org.evergreen.verse.lambda;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.evergreen.client.AmazonDynamoDBProxy;
import org.evergreen.client.AmazonPollyProxy;
import org.evergreen.client.AmazonS3Proxy;
import org.evergreen.client.IBiblesNetProxy;
import org.evergreen.lambda.common.LambdaEnvironmentVariables;
import org.evergreen.verse.DateRequest;
import org.evergreen.verse.handler.*;

public class GetRegionLambdaFunction implements RequestHandler<DateRequest, EvergreenQTPassageResult> {

    private LambdaLogger logger;
    private static AmazonDynamoDBProxy dynamoDB;
    private static AmazonS3Proxy s3Proxy;
    private static AmazonPollyProxy pollyProxy;
    private static IBiblesNetProxy iBiblesNetProxy;

    @Override
    public EvergreenQTPassageResult handleRequest(final DateRequest request, final Context context) {
        logger = context.getLogger();
        logger.log(String.format("Test request received with year: %s, month: %s, day: %s.", request.getYear(), request.getMonth(), request.getDay()));

        initiateSingletons();
        logger.log("Finished initializing the clients");

        final EvergreenQTTableHandler evergreenQTTableHandler = new EvergreenQTTableHandler(dynamoDB, request.toDateTime());
        final EvergreenQTBucketHandler evergreenQTBucketHandler = new EvergreenQTBucketHandler(s3Proxy, request.toDateTime());
        final EvergreenQTTTSHandler evergreenQTTTSHandler = new EvergreenQTTTSHandler(pollyProxy, evergreenQTBucketHandler);
        final EvergreenQTPassageHandler evergreenQTPassageHandler = new EvergreenQTPassageHandler(evergreenQTTableHandler, evergreenQTTTSHandler, iBiblesNetProxy);

        return evergreenQTPassageHandler.handle();
    }

    public static EvergreenQTPassageResult handleRequest(final DateRequest request){
        initiateSingletons();

        final EvergreenQTTableHandler evergreenQTTableHandler = new EvergreenQTTableHandler(dynamoDB, request.toDateTime());
        final EvergreenQTBucketHandler evergreenQTBucketHandler = new EvergreenQTBucketHandler(s3Proxy, request.toDateTime());
        final EvergreenQTTTSHandler evergreenQTTTSHandler = new EvergreenQTTTSHandler(pollyProxy, evergreenQTBucketHandler);
        final EvergreenQTPassageHandler evergreenQTPassageHandler = new EvergreenQTPassageHandler(evergreenQTTableHandler, evergreenQTTTSHandler, iBiblesNetProxy);

        return evergreenQTPassageHandler.handle();
    }

    private static void initiateSingletons() {
        final Regions region = LambdaEnvironmentVariables.getRegion();
        dynamoDB = new AmazonDynamoDBProxy(region);
        s3Proxy = new AmazonS3Proxy(region);
        pollyProxy = new AmazonPollyProxy(region);
        iBiblesNetProxy = new IBiblesNetProxy();
    }
}
