package com.example.j2ee.config;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Swagger3",
                version = "3.0",
                description = "Swagger3使用演示"
        ),
        externalDocs = @ExternalDocumentation(
                description = "参考文档",
                url = "https://github.com/swagger-api/swagger-core/wiki/Swagger-2.X---Annotations"
        )
)
public class SwaggerConfig
{

}