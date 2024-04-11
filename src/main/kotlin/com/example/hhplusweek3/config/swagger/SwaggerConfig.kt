package com.example.hhplusweek3.config.swagger

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {
    @Bean
    fun openAPI(): OpenAPI {
        return OpenAPI()
            .components(Components())
            .info(configurationInfo())

    }

    private fun configurationInfo(): Info {
        return Info()
            .title("HHPlusWeek3 API")
            .description("HHPlusWeek3 API")
            .version("1.0.0")
    }
}