package com.ssafy.s3;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class ProductAwsS3ServiceImpl implements ProductAwsS3Service {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;
    
    @Autowired
    public ProductAwsS3ServiceImpl(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    @Override
    public String fileUpload(String category, MultipartFile uploadFile) throws IOException {
    	
    	ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(uploadFile.getInputStream().available());

        objectMetadata.setContentType(uploadFile.getContentType());
        String fileNameString = String.valueOf(getRandomUuid());
        amazonS3.putObject(new PutObjectRequest(bucket, fileNameString, uploadFile.getInputStream(), objectMetadata));

        return amazonS3.getUrl(bucket, fileNameString).toString();
    }

    private static UUID getRandomUuid() {
        return UUID.randomUUID();
    }
}
