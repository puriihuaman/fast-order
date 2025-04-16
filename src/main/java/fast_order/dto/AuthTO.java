package fast_order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) for the user authentication process.
 * -
 * Contains the user credentials (email and password) used to generate a JWT token.
 * It is used in login requests and is subject to strict format validations.
 * -
 * Lombok annotations ({@code @Builder}, {@code @Getter}, {@code @Setter}) automatically generate
 * the builder pattern, getters, and setters.
 * Validation annotations ({@code @NotNull}, {@code @Email}, {@code @Pattern}) ensure compliance
 * with security policies.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@Setter
@Schema(
    name = "Authentication",
    description = "DTO used in the login process to validate user credentials and generate a JWT Token."
)
public class AuthTO {
    @Schema(
        description = "User email", example = "jorge@gmail.com",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "{field.null}")
    @NotEmpty(message = "{field.empty}")
    @Email(message = "{auth.email}")
    @Size(max = 60)
    @JsonProperty(value = "email")
    private String email;
    
    @Schema(
        description = """
                      User password.
                      Must be at least 8 characters, including one uppercase letter,
                      one lowercase letter, one number, and one special character.
                      """, example = "J0rG3#B&t3s", requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "{field.null}")
    @NotEmpty(message = "{field.empty}")
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{8,}$", message = "{user.password}"
    )
    @JsonProperty(value = "password")
    private String password;
}
