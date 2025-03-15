package com.example.demo.AWS;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Service
public class S3Service {

    private final S3Client s3;

    public S3Service(S3Client s3) {
        this.s3 = s3;
    }

    /**
     * Uploads a file to an AWS S3 bucket
     * @param bucketName The name of the bucket you want this image to go to 
     * @param file File to upload
     * @return Public URL to download/view the image
     * @throws IOException
     */
    public String putObject(String bucketName, MultipartFile file) throws IOException
    {
        // Get the time stamp to append to file name - ensures no file names conflict
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmmss");
        String timestamp = dateFormat.format(new Date());

        // Append timestamp to file name
        String key = timestamp + file.getOriginalFilename();

        // Build request - from AWS documentation
        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .acl(ObjectCannedACL.PUBLIC_READ)
                .build();
        
        // Put the file in the bucket
        s3.putObject(objectRequest, RequestBody.fromBytes(file.getBytes()));

        // Return public URL for the file you just uploaded
        return "https://" + bucketName + ".s3.us-west-2.amazonaws.com/" + key;

    }



}
