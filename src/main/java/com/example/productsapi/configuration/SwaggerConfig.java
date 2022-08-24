package com.example.productsapi.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

/**
 * @implNote <b>Swagger Configuration</b>
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Products API Service").version("v1.0.0")
                        .description("An API about everything about products.")
                        .termsOfService("Terms of Service").license(getLicense()).contact(getContact()));

    }

    private Contact getContact() {
        Contact contact = new Contact();
        contact.setEmail("xolisamatika@gmail.com");
        contact.setName("Xolisa Matika");
        contact.setUrl("https://www.linkedin.com/in/xolisamatika/");
        contact.setExtensions(Collections.emptyMap());
        return contact;
    }

    private License getLicense() {
        License license = new License();
        license.setName("Apache License, Version 2.0");
        license.setUrl("https://opensource.org/licenses/Apache-2.0");
        license.setExtensions(Collections.emptyMap());
        return license;
    }

}
