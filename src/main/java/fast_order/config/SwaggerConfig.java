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


@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "FAST ORDER API", version = "1.0.0",
        description = "API Rest que permite gestionar pedidos de productos de manera eficiente y segura.",
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
