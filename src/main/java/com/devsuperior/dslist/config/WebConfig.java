package com.devsuperior.dslist.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    /*

        @Bean
        public WebMvcConfigurer corsConfigurer() {
            return new WebMvcConfigurer() {
                @Override
                public void addCorsMappings(CorsRegistry registry) {
                    registry.addMapping("/**") // todas as rotas
                        .allowedOrigins("http://localhost:4200") // front Angular
                        .allowedMethods("GET", "POST", "PUT", "DELETE") // métodos permitidos
                        .allowedHeaders("*");
                }
            };
        }
    */

    // Configuração CORS alternativa usando CorsConfigurationSource no SecurityConfig
    // Com isso vou deixar esse arquivo WebConfig.java apenas como referência
}