package fast_order.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

/**
 * Configuring Swagger for API documentation.
 * This class uses OpenAPI (Swagger 3) annotation to define general API information and configure
 * the JWT (JSON Web Tokens) based security scheme.
 * -
 * {@code @Configuration} provides bean definitions for the application, although in this case,
 * it is primarily used for declarative configuration of Swagger via annotations.
 * {@code @OpenAPIDefinition} defines the API metadata.
 * {@code @SecuritySchema} configures the "Security Token" authentication scheme to use JWT in
 * the HTTP request header.
 */
@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "FAST ORDER API", version = "1.0.0",
        description = "Rest API that allows you to manage product orders efficiently and securely.",
        termsOfService = "https://swagger.io/terms/", contact = @Contact(
        name = "Pedro Purihuaman", url = "https://puriihuaman.netlify.app/",
        email = "pedropuriihuaman@gmail.com"
    )
    ), servers = {
    @Server(description = "DEV SERVER", url = "http://localhost:8080/api"),
    @Server(description = "PROD SERVER", url = "https://example:8080/api")
}, security = @SecurityRequirement(name = "Security Token")
)
@SecurityScheme(
    name = "Security Token", description = "Access token for My API",
    type = SecuritySchemeType.HTTP, paramName = HttpHeaders.AUTHORIZATION,
    in = SecuritySchemeIn.HEADER, scheme = "bearer", bearerFormat = "JWT"
)
public class SwaggerConfig {}
