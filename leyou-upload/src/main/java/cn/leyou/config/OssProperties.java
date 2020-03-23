package cn.leyou.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author ：xiaKe
 * @date ：Created in 2020/3/23 23:50
 * @description：
 */
@Component
@ConfigurationProperties(prefix = "ly.oss")
@Data
public class OssProperties {
    private String accessKeyId;
    private String accessKeySecret;
    private String bucket;
    private String host;
    private String endpoint;
    private String dir;
    private long expireTime;
    private long maxFileSize;

}
