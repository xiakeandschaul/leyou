package cn.leyou.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableConfigurationProperties(CorsProperties.class)
public class GlobalCorsConfig {
    @Bean
    public CorsFilter corsFilter(CorsProperties pop) {
        CorsConfiguration config = new CorsConfiguration();
        pop.getAllowedOrigins().forEach(config::addAllowedOrigin);
        pop.setAllowCredentials(pop.getAllowCredentials());
        pop.getAllowedHeaders().forEach(config::addAllowedHeader);
        pop.getAllowedMethods().forEach(config::addAllowedMethod);
        pop.setMaxAge(pop.getMaxAge());
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(pop.getFilterPath(), config);
        return  new CorsFilter(source);
    }
}
