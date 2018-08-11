package org.evergreen.verse.handler;

import org.evergreen.client.AmazonS3Proxy;
import org.joda.time.DateTime;

import java.io.File;

public class EvergreenQTBucketHandler {

    private final String BUCKET_NAME = "evergreenqt";
    private final DateTime dateTime;
    private final AmazonS3Proxy amazonS3Proxy;

    public EvergreenQTBucketHandler(final AmazonS3Proxy amazonS3Proxy, final DateTime dateTime) {
        this.amazonS3Proxy = amazonS3Proxy;
        this.dateTime = dateTime;
    }

    public String generateKey() {
        return String.format("%s-%s-%s", dateTime.getYear(), dateTime.getMonthOfYear(), dateTime.getDayOfMonth());
    }

    public String putObject(final File file) {
        return putObject(generateKey(), file);
    }

    public String putObject(final String key, final File file) {
        return amazonS3Proxy.putObject(BUCKET_NAME, key, file);
    }
}
