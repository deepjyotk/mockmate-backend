package com.mockmate.auth_service.service.aws;

import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

public interface AWSS3Service {
    String uploadMarkdownFile(MultipartFile file, Long interviewTypeId, String questionTitle,
                              Long difficultyId, Long companyId) throws Exception;

    String generatePresignedUrl(String objectKey, Date expiration);

}