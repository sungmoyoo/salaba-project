package org.admin.service;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    public void download(String bucketName, String objectName, String downloadFilePath) throws Exception;
}