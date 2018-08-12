package org.evergreen.client;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.StorageClass;

import java.io.File;

public class AmazonS3Proxy {
    private final AmazonS3 amazonS3;
    private final String URI_PREFIX = "https://s3.amazonaws.com/";

    public AmazonS3Proxy(final Regions region) {
        amazonS3 = AmazonS3ClientBuilder.standard()
            .withRegion(region)
            .build();
    }

    public String putObject(final String bucketName, final String key, final File file) {
        amazonS3.putObject(createPutObjectRequest(bucketName, key, file));
        return generateS3Path(bucketName, key);
    }

    public String generateS3Path(final String bucketName, final String key) {
        return String.format("%s%s/%s", URI_PREFIX, bucketName, key);
    }

    public PutObjectRequest createPutObjectRequest(final String bucketName, final String key, final File file) {
        return new PutObjectRequest(bucketName, key, file)
                .withStorageClass(StorageClass.ReducedRedundancy);
    }
}
