package cn.leyou.service.impl;

import cn.leyou.config.OssProperties;
import cn.leyou.enums.ExceptionEnum;
import cn.leyou.exception.LyException;
import cn.leyou.service.UploadService;
import com.aliyun.oss.OSS;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @author ：xiaKe
 * @date ：Created in 2020/3/23 21:43
 * @description：
 */
@Service
public class UploadServiceImpl implements UploadService {
    @Autowired
    private OssProperties prop;
    @Autowired
    private OSS client;

    private static final List<String> suffix = Arrays.asList("image/png", "image/jpeg", "image/bmp");

    @Override
    public String uploadImage(MultipartFile file) {
//        验证文件类型
        String type = file.getContentType();
        if (!suffix.contains(type)) {
            throw new LyException(ExceptionEnum.INVALID_FILE_TYPE);
        }
        BufferedImage image = null;
        try {
            image = ImageIO.read(file.getInputStream());
        } catch (IOException e) {
            throw new LyException(ExceptionEnum.INVALID_FILE_TYPE);
        }
        if (image == null) {
            throw new LyException(ExceptionEnum.INVALID_FILE_TYPE);
        }
//        保存图片再nginx的html下
        File dir = new File("F:\\software\\nginx-1.12.2\\html\\");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try {
            file.transferTo(new File(dir, file.getOriginalFilename()));
            return "http://image.leyou.com/" + file.getOriginalFilename();
        } catch (IOException e) {
            throw new LyException(ExceptionEnum.IMAGE_UPLOAD_ERROR);
        }
    }

    @Override
    public Map<String, Object> getSignature() {
        long expireTime = prop.getExpireTime();
        long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
        Date date = new Date(expireEndTime);
        PolicyConditions conditions = new PolicyConditions();
        conditions.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, prop.getMaxFileSize());
        conditions.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, prop.getDir());
        String postPolicy = client.generatePostPolicy(date, conditions);
        try {
            byte[] bytes = postPolicy.getBytes("utf-8");
            String encodePolicy = BinaryUtil.toBase64String(bytes);
            String signature = client.calculatePostSignature(postPolicy);
            LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<>();
            linkedHashMap.put("accessId", prop.getAccessKeyId());
            linkedHashMap.put("policy", encodePolicy);
            linkedHashMap.put("signature", signature);
            linkedHashMap.put("dir", prop.getDir());
            linkedHashMap.put("host", prop.getHost());
            linkedHashMap.put("expire", expireEndTime);
            return linkedHashMap;
        } catch (UnsupportedEncodingException e) {
            throw new LyException(ExceptionEnum.UPLOAD_IMAGE_FAIL);
        }
    }
}
