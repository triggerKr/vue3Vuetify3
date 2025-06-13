package exdev.config;


import java.io.File;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import exdev.com.common.ExdevConstants;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // /uploadFiles/** 경로로 요청이 들어오면, 시스템의 D:/OCI/workspace/exdev/uploadFiles/ 디렉토리에서 리소스를 찾음
        String path = ExdevConstants.FILE_DIRECTORY_PATH + File.separator + ExdevConstants.FILE_UPLOAD_PATH + File.separator + ExdevConstants.EDITOR_PATH+ File.separator ;
        
        registry.addResourceHandler("/uploadFiles/editorFiles/**")
                .addResourceLocations("file:"+path);
    }
    
    @Bean
    public WebMvcConfigurer corsConfigurer() {
      return new WebMvcConfigurer() {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
          registry.addMapping("/**")
            .allowedOrigins("http://localhost:8080")  // Vue 개발 서버 주소
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .allowedHeaders("*")
            .allowCredentials(true);
        }
      };
    }
}