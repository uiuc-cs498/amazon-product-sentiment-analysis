package com.kg.lambda;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.ScheduledEvent;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3ObjectSummary;

public class PublicAWSReviewsS3 implements RequestHandler<ScheduledEvent, String> {

	@Override
	public String handleRequest(ScheduledEvent event, Context context) {
		context.getLogger().log("Invoked event: " + event);
		// TODO Auto-generated method stub
		AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(DefaultAWSCredentialsProviderChain.getInstance())
                .withRegion("us-east-1")
                .build();

        
        ListObjectsV2Request listObjectsRequest = new ListObjectsV2Request()
        	    .withBucketName("amazon-reviews-pds")
        	    .withPrefix("tsv");
        ListObjectsV2Result objectListing;

        	do {
        	        objectListing = s3Client.listObjectsV2(listObjectsRequest);
        	        for (S3ObjectSummary objectSummary : 
        	            objectListing.getObjectSummaries()) {
        	        	context.getLogger().log(" - " + objectSummary.getKey() + "  " +
        	                    "(size = " + objectSummary.getSize() + 
        	                    " last modified = "+objectSummary.getLastModified()
        	                    +")");
        	        }
        	        String token = objectListing.getNextContinuationToken();
        	        context.getLogger().log("Next Continuation Token: " + token);
        	        listObjectsRequest.setContinuationToken(token);
        	} while (objectListing.isTruncated());
		return null;
	}
}
