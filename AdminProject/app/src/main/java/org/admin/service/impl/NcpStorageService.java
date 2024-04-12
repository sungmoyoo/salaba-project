package org.admin.service.impl;

import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.admin.service.StorageService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

@Service
public class NcpStorageService implements StorageService, InitializingBean {
    private final static Log log = LogFactory.getLog(NcpStorageService.class);
    private final String endPoint;
    private final String regionName;
    private final String accessKey;
    private final String secretKey;
    private final AmazonS3 s3;

    public NcpStorageService(@Value("${ncp.ss.endpoint}") String endPoint,
                             @Value("${ncp.ss.regionname") String regionName,
                             @Value("${ncp.accesskey}") String accessKey,
                             @Value("${ncp.secretkey}") String secretKey
    ) {
        this.endPoint = endPoint;
        this.regionName = regionName;
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.s3 = AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endPoint, regionName))
                .withCredentials(
                        new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
                .build();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.debug(String.format("endPoint: %s", this.endPoint));
        log.debug(String.format("regionName: %s", this.regionName));
        log.debug(String.format("accessKey: %s", this.accessKey));
        log.debug(String.format("secretKey: %s", this.secretKey));
    }


    @Override
    public void download(String bucketName, String objectName, String downloadFilePath) throws Exception {
        // download object
        try {
            S3Object s3Object = s3.getObject(bucketName, objectName);
            S3ObjectInputStream s3ObjectInputStream = s3Object.getObjectContent();

            OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(downloadFilePath));
            byte[] bytesArray = new byte[4096];
            int bytesRead = -1;
            while ((bytesRead = s3ObjectInputStream.read(bytesArray)) != -1) {
                outputStream.write(bytesArray, 0, bytesRead);
            }

            outputStream.close();
            s3ObjectInputStream.close();
            System.out.format("Object %s has been downloaded.\n", objectName);
        } catch (AmazonS3Exception e) {
            e.printStackTrace();
        } catch (SdkClientException e) {
            e.printStackTrace();
        }
    }
}
