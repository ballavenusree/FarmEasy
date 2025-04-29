package com.farmsy.config;

import com.farmsy.model.Crop;
import com.farmsy.model.Farmer;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {



    @Bean
    public OpenAPI farmEasyOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("FarmEasy API"))
                .components(new Components()
                        .addSchemas("Farmer", new Schema<Farmer>()
                                .example(getFarmerExample()))
                        .addSchemas("Crop", new Schema<Crop>()
                                .example(getCropExample())));
    }

    private Farmer getFarmerExample() {
        Farmer example = new Farmer();
        example.setName("John Farmer");
        example.setEmail("john.farmer@example.com");
        example.setPassword("Secure@123");
        example.setPhone("9876543210");
        example.setAddress("123 Farm Road");
        example.setRegion("North");
        return example;
    }

    private Crop getCropExample() {
        Crop example = new Crop();
        example.setName("Wheat");
        example.setType("Grain");
        example.setQuantity(100.0);
        example.setPricePerUnit(2.5);
        example.setHarvestDate("2023-10-15");
        example.setAvailable(true);
        return example;
    }
}