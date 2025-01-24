package pray.ryan.trigger.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {
    @Bean
    fun createOpenApi(): OpenAPI {
        val components = Components()
        val openApi = OpenAPI()
            .components(components)
            .info(apiInfo())

        return openApi
    }

    private fun apiInfo(): Info {
        return Info()
            .title("Trigger API")
            .description("Trigger API Swagger UI")
            .version("1.0.0")
    }
}