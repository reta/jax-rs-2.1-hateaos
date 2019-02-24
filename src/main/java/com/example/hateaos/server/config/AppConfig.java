package com.example.hateaos.server.config;

import org.apache.cxf.jaxrs.openapi.OpenApiFeature;
import org.apache.cxf.jaxrs.swagger.ui.SwaggerUiConfig;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

@SpringBootConfiguration
public class AppConfig {
    @Bean
    OpenApiFeature createOpenApiFeature() {
        final OpenApiFeature openApiFeature = new OpenApiFeature();
        openApiFeature.setSwaggerUiConfig(new SwaggerUiConfig().url("/api/openapi.json"));
        return openApiFeature;
    }
    
    @Bean
    JacksonJsonProvider jacksonJsonProvider() {
        return new JacksonJsonProvider();
    }
}
