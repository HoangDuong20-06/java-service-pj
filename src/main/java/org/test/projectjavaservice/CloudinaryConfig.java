package org.test.projectjavaservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "ten_cloud_cua_ban",
                "api_key", "ma_api_key_cua_ban",
                "api_secret", "ma_api_secret_cua_ban"
        ));
    }
}
