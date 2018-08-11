package org.evergreen.client;

import com.amazonaws.regions.Regions;
import org.junit.Test;

public class AmazonDynamoDBProxyTest {

    @Test
    public void isAbleToCreateClient() {
        new AmazonDynamoDBProxy(Regions.US_EAST_1);
    }

    /*
    @Test
    public void retrieveItem() {
        final AmazonDynamoDBProxy proxy = new AmazonDynamoDBProxy(Regions.US_EAST_1);
        final Item item = proxy.retrieveItem("EvergreenQT", new PrimaryKey("Date", "2018-8-11"));

        final String entry = item.getString("Something");
        System.out.println(entry);
        final Optional<String> optionalString = Optional.ofNullable(entry);
        System.out.println(optionalString);

        final String rangeEntry = item.getString("Range");
        System.out.println(rangeEntry);
        final Optional<String> optionalStringRange = Optional.ofNullable(rangeEntry);
        System.out.println(optionalStringRange);
    }*/

    /*
    @Test
    public void retrieveItem() {
        final AmazonDynamoDBProxy proxy = new AmazonDynamoDBProxy(Regions.US_EAST_1);
        proxy.updateItemStringAttribute("EvergreenQT", new PrimaryKey("Date", "2018-8-11"),
                "Test", "TestValue");
    }*/
}
