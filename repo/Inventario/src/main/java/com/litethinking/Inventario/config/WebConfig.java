package com.litethinking.Inventario.config;

import io.swagger.models.HttpMethod;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://react-inv-app.s3-website.us-east-2.amazonaws.com",
                                "http://localhost:3000")
                        .allowedMethods(HttpMethod.GET.name(),HttpMethod.POST.name(),HttpMethod.PUT.name(),HttpMethod.DELETE.name())
                        .allowedHeaders("*")
                        .exposedHeaders("Authorization")
                        .allowCredentials(true);
            }
        };
    }
}
