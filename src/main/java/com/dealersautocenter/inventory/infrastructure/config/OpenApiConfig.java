package com.dealersautocenter.inventory.infrastructure.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.HeaderParameter;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Value("${tenant.header-name:X-Tenant-Id}")
    private String tenantHeaderName;

    @Value("${tenant.role-header-name:X-User-Role}")
    private String roleHeaderName;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addParameters("tenantId", new Parameter()
                                .in("header")
                                .name(tenantHeaderName)
                                .description("Tenant Identifier")
                                .required(true)
                                .schema(new StringSchema()))
                        .addParameters("userRole", new Parameter()
                                .in("header")
                                .name(roleHeaderName)
                                .description("User Role (GLOBAL_ADMIN, TENANT_USER)")
                                .required(false)
                                .schema(new StringSchema()._default("TENANT_USER")))
                );
    }

    @Bean
    public OpenApiCustomizer customerContextCustomizer() {
        return openApi -> {
            if (openApi.getPaths() != null) {
                openApi.getPaths().values().forEach(pathItem -> 
                    pathItem.readOperations().forEach(operation -> {
                        operation.addParametersItem(new HeaderParameter().$ref("#/components/parameters/tenantId"));
                        operation.addParametersItem(new HeaderParameter().$ref("#/components/parameters/userRole"));
                    })
                );
            }
        };
    }
}
