package com.pro.helper;

import cn.hutool.core.lang.UUID;
import com.aliyun.oss.OSSClient;
import com.pro.enums.FileType;
import com.pro.exception.GlobalException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;


@Slf4j
@Component
@ConfigurationProperties(prefix = "oss")
@Data
public class OssHelper implements InitializingBean {

    private String accessKey;
    private String secretKey;
    private String endpoint;
    private String bucketName;
    private String staticDomain;
    private OSSClient ossClient;
    private final String filePath = "cys/";

    public String  uploadFile(MultipartFile uploadFile, FileType fileType) throws GlobalException {
        String originFileName = uploadFile.getOriginalFilename();
        String suffix =originFileName.substring(originFileName.lastIndexOf(".")+1);
        if(fileType!=null&&!fileType.getSuffix().contains(suffix.toLowerCase())){
            throw new GlobalException("文件格式不允许上传");
        }
        String fileName = filePath+createFileName(suffix);
        try {
            ossClient.putObject(bucketName,fileName,new ByteArrayInputStream(uploadFile.getBytes()));
        } catch (Exception e) {
           log.error("Oss上传文件失败",e);
           throw new GlobalException(e.getMessage());
        }
        return staticDomain+fileName;
    }

    private String createFileName(String suffix){
        return UUID.fastUUID().toString().replaceAll("-","").concat(".").concat(suffix);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ossClient = new OSSClient(endpoint,accessKey,secretKey);
    }

}