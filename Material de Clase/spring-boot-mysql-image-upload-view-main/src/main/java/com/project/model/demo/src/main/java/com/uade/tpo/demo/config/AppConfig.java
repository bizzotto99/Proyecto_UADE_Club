package com.uade.tpo.demo.config;

import org.springframework.boot.autoconfigure.web.servlet.MultipartProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

@Configuration
public class AppConfig {

    @Bean
    public StandardServletMultipartResolver multipartResolver(MultipartProperties multipartProperties) {
        return new StandardServletMultipartResolver();
    }
}
