package org.evergreen.client;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.StorageClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class AmazonS3ProxyTest {

    @Mock
    File fileMock;

    @Test
    public void isAbleToCreateClient() {
        final AmazonS3Proxy s3Proxy = new AmazonS3Proxy(Regions.US_EAST_1);
    }

    @Test
    public void createPutObjectRequestUsesReducedRedundancy() {
        final AmazonS3Proxy s3Proxy = new AmazonS3Proxy(Regions.US_EAST_1);

        final PutObjectRequest putObjectRequest = s3Proxy.createPutObjectRequest("BucketName", "Key", fileMock);

        assertEquals(StorageClass.ReducedRedundancy.toString(), putObjectRequest.getStorageClass());
        assertEquals("BucketName", putObjectRequest.getBucketName());
        assertEquals("Key", putObjectRequest.getKey());
    }
}
