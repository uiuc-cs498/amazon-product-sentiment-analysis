package com.kg.lambda;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.Region;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import com.kg.lambda.AWSReview;

public class S3FileToChunks implements RequestHandler<S3Event, String> {

	private AmazonS3 s3 = AmazonS3ClientBuilder.standard().build();
	
	public String handleRequest(S3Event event, Context context) {
		
		context.getLogger().log("Received event: " + event);
		
		// Get the object from the event and show its content type
        String bucket = event.getRecords().get(0).getS3().getBucket().getName();
        String key = event.getRecords().get(0).getS3().getObject().getKey();
        
        context.getLogger().log("Bucket Name: "+bucket);
        context.getLogger().log("Key Name: "+key);
        
        Integer chunkCounter = 0;
        String chunkName = key.substring(key.indexOf("\\/")+1,key.indexOf("."));
        
        try {
        	S3Object response = s3.getObject(new GetObjectRequest(bucket, key));
            String contentType = response.getObjectMetadata().getContentType();
            
            context.getLogger().log("File Name: "+response.getKey());
            context.getLogger().log("File Size: "+(response.getObjectMetadata().getContentLength()/(1024*1024))+" MB");
        	
            Pattern pattern = Pattern.compile("\t");
            String tsvOutput;
            long counter = 0;
            int n = 100; // first n lines to be displayed
            List<AWSReview> arvwlist= new ArrayList();
            AWSReview ar;
            
            try(BufferedReader br = new BufferedReader(new InputStreamReader(new GZIPInputStream(response.getObjectContent())))){
            	br.readLine();//skipping header
                while ((tsvOutput = br.readLine()) != null) {
                    //String[] str = tsvOutput.split("\t");
                    //if(counter < n) context.getLogger().log("first "+n+" lines: "+tsvOutput);
                	String[] x = pattern.split(tsvOutput);
                	ar = new AWSReview(x[0], x[1], x[2], x[3], x[4], x[5], x[6], x[7], x[8], x[9], x[10], x[11], x[12],
    						x[13], x[14]);
                	
                	arvwlist.add(ar);
                	
                	if(arvwlist.size()==n) {
                		ObjectMapper mapper = new ObjectMapper();
            		    mapper.enable(SerializationFeature.INDENT_OUTPUT);
            		    //mapper.writeValue(System.out, arvwlist);
            		    
            		    byte[] bytes = mapper.writeValueAsBytes(arvwlist);
            		    
            		    InputStream inputStream = new ByteArrayInputStream(bytes);
            		    
            		    ObjectMetadata om = new ObjectMetadata();
            		    om.setContentLength(bytes.length);
            		    om.setContentType("application/json");
            		    
            		    context.getLogger().log("chunk_"+(chunkCounter+1)+" "+arvwlist);
            		    PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, "dest/chunk_"+(chunkCounter++)+chunkName, inputStream, om);
            		    s3.putObject(putObjectRequest);

            		    arvwlist = new ArrayList();// for new chunk
                	}
                    counter++;
                }
            	
            } catch (Exception e) {
            	e.printStackTrace();
            }
            
            
            context.getLogger().log("# of lines: "+counter);
            
            //AmazonS3 s3Client = new AmazonS3Client(DefaultAWSCredentialsProviderChain.getInstance());

            // read the list of objects from public s3 bucket
            /* AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withCredentials(new ProfileCredentialsProvider())
                    .withRegion("us-east-1")
                    .build(); */
            
            //AmazonS3 s3Client = new AmazonS3Client(DefaultAWSCredentialsProviderChain.getInstance());
            
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
            	        	/*context.getLogger().log(" - " + objectSummary.getKey() + "  " +
            	                    "(size = " + objectSummary.getSize() + 
            	                    " last modified = "+objectSummary.getLastModified()
            	                    +")");*/
            	        }
            	        String token = objectListing.getNextContinuationToken();
                        //System.out.println("Next Continuation Token: " + token);
            	        listObjectsRequest.setContinuationToken(token);
            	} while (objectListing.isTruncated());
            
            
            return contentType;
            
        }catch (Exception e) {
            e.printStackTrace();
            context.getLogger().log(String.format("Error getting object %s from bucket %s. Make sure they exist and"
                    + " your bucket is in the same region as this function.", bucket, key));
            return e.toString();
        }
        
		// TODO Auto-generated method stub
		//return null;
	}
	

}
