package org.evergreen.verse.handler;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import org.evergreen.client.AmazonDynamoDBProxy;
import org.evergreen.client.AmazonPollyProxy;
import org.evergreen.client.AmazonS3Proxy;
import org.evergreen.client.IBiblesNetProxy;
import org.evergreen.verse.DateRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class EvergreenQTPassageHandlerTest {

    @Mock
    LambdaLogger lambdaLogger;

    private AmazonDynamoDBProxy dynamoDB;
    private AmazonS3Proxy s3Proxy;
    private AmazonPollyProxy pollyProxy;
    private IBiblesNetProxy iBiblesNetProxy;

    private EvergreenQTTableHandler evergreenQTTableHandler;
    private EvergreenQTBucketHandler evergreenQTBucketHandler;
    private EvergreenQTTTSHandler evergreenQTTTSHandler;
    private EvergreenQTPassageHandler evergreenQTPassageHandler;

    @Before
    public void setup() {
        final DateRequest request = new DateRequest();
        request.setYear("2018");
        request.setMonth("8");
        request.setDay("12");

        final Regions region = Regions.US_EAST_1;
        dynamoDB = new AmazonDynamoDBProxy(region);
        s3Proxy = new AmazonS3Proxy(region);
        pollyProxy = new AmazonPollyProxy(region);
        iBiblesNetProxy = new IBiblesNetProxy();

        evergreenQTTableHandler = new EvergreenQTTableHandler(dynamoDB, request.toDateTime());
        evergreenQTBucketHandler = new EvergreenQTBucketHandler(s3Proxy, request.toDateTime());
        evergreenQTTTSHandler = new EvergreenQTTTSHandler(pollyProxy, evergreenQTBucketHandler);
        evergreenQTPassageHandler = new EvergreenQTPassageHandler(evergreenQTTableHandler, evergreenQTTTSHandler, iBiblesNetProxy);
    }

    @Test
    public void empty() {
        assertEquals(1,1);
    }

    /*
    @Test
    public void test() {
        System.out.println(evergreenQTPassageHandler.handle());
    }*/
}
