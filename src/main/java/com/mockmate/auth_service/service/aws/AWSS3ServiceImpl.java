package com.mockmate.auth_service.service.aws;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.Date;

@Service
public class AWSS3ServiceImpl implements AWSS3Service {

    private final AmazonS3 amazonS3;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    public AWSS3ServiceImpl(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    @Override
    public String uploadMarkdownFile(MultipartFile file, Long interviewTypeId, String questionTitle,
                                     Long difficultyId, Long companyId) throws Exception {
        // Validate markdown file
        if (!isValidMarkdown(file)) {
            throw new IllegalArgumentException("Invalid markdown file.");
        }

        // Define the S3 object key/path
        String objectKey = String.format("%d/%s.md", interviewTypeId, sanitizeFileName(questionTitle));

        // Prepare metadata
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("text/markdown");
        metadata.setContentLength(file.getSize());

        // Add custom metadata
        metadata.addUserMetadata("difficultyId", difficultyId.toString());
        if (companyId != null) {
            metadata.addUserMetadata("companyId", companyId.toString());
        }

        // Upload to S3
        try {
            amazonS3.putObject(new PutObjectRequest(bucketName, objectKey, file.getInputStream(), metadata)
                    .withCannedAcl(CannedAccessControlList.Private));
        } catch (IOException e) {
            throw new Exception("Failed to upload file to S3.", e);
        }

        // Return the S3 URL
        return amazonS3.getUrl(bucketName, objectKey).toString();
    }

    @Override
    public String generatePresignedUrl(String objectKey, Date expiration) {
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucketName, objectKey)
                        .withMethod(HttpMethod.GET)
                        .withExpiration(expiration);

        URL url = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);
        return url.toString();
    }

    private boolean isValidMarkdown(MultipartFile file) {
        // Simple validation: check file extension and content type
        String filename = file.getOriginalFilename();
        if (filename == null || !filename.toLowerCase().endsWith(".md")) {
            return false;
        }

        String contentType = file.getContentType();
        return contentType != null && (contentType.equals("text/markdown") || contentType.equals("text/plain"));
    }

    private String sanitizeFileName(String fileName) {
        // Replace spaces with underscores and remove special characters
        return fileName.trim().replaceAll(" ", "_").replaceAll("[^a-zA-Z0-9_\\-]", "");
    }
}