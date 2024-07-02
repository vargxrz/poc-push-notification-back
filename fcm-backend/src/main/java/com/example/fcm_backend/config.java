package com.example.fcm_backend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class config  implements WebMvcConfigurer{
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")  // Substitua pelo domínio que você deseja permitir
                .allowedMethods("*")  // Métodos permitidos
                .allowedHeaders("*")  // Cabeçalhos permitidos
                .allowCredentials(true);  // Permite enviar credenciais
    }
}
