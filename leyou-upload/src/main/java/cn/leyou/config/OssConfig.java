package cn.leyou.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ：xiaKe
 * @date ：Created in 2020/3/23 23:51
 * @description：
 */
@Configuration
public class OssConfig {
    @Bean
    public OSS ossClient(OssProperties prop) {
        return new OSSClientBuilder().build(prop.getEndpoint(), prop.getAccessKeyId(), prop.getAccessKeySecret());
    }
}
