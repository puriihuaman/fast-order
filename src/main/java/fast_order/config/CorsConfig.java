package fast_order.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuring CORS (Cross-Origin Resource Sharing) for the API.
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    /**
     * Configure CORS policies for the application.
     * This method is automatically invoked by Spring MVC to register CORS mappings.
     * In this implementation, a global mapping is configured for all API routes.
     *
     * @param registry The CORS mapping registry allows you to define rules for sharing resources
     *                 between origins.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*").allowedMethods(
            HttpMethod.GET.name(),
            HttpMethod.POST.name(),
            HttpMethod.PUT.name(),
            HttpMethod.DELETE.name(),
            HttpMethod.OPTIONS.name(),
            HttpMethod.HEAD.name(),
            HttpMethod.PATCH.name()
        );
    }
}
