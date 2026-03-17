package com.ordermanagement.productservice.config;



import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigSwagger {

        @Bean
        public OpenAPI customOpenAPI() {
            return new OpenAPI()
                    .info(new Info()
                            .title("Product & Inventory Service API")
                            .version("1.0")
                            .description("""
                            This API manages products and inventory.

                            Business Rules:
                            - Each product has a unique ID
                            - Stock cannot go below zero
                            - Product can be ACTIVE or INACTIVE
                            - Deactivated products cannot be used for operations
                            - Inventory is maintained separately per product
                            """)
                    );
        }
    }

