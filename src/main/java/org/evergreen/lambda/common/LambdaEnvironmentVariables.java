package org.evergreen.lambda.common;

import com.amazonaws.regions.Regions;

//https://docs.aws.amazon.com/lambda/latest/dg/current-supported-versions.html#lambda-environment-variables
public class LambdaEnvironmentVariables {

    public static final String AWS_DEFAULT_REGION = "AWS_DEFAULT_REGION";

    public static Regions getRegion() {
        return Regions.fromName(System.getenv(AWS_DEFAULT_REGION));
    }
}
