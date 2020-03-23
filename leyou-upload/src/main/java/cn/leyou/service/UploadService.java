package cn.leyou.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface UploadService {
    String uploadImage(MultipartFile file);

    Map<String,Object> getSignature();

}
