package fast_order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object (DTO) for the user authentication process.
 * -
 * Contains the user's credentials (email and password) used to generate a JWT token.
 * It is used in login requests and is subject to strict format and security validations.
 * -
 * Lombok annotations ({@code @Getter}, {@code @Setter} implicit in record) automatically generate
 * field access methods.
 * Validation annotations ({@code @NotNull}, {@code @Email}, {@code @Pattern}) ensure
 * compliance with security policies and required formats.
 *
 * @param email    The user's email address. This must be a valid email address and no
 *                 longer than 60 characters.
 * @param password The user's password. It must be at least 8 characters long and contain at
 *                 least one uppercase letter, one lowercase letter, one number,
 *                 and one special character.
 */
@Schema(
    name = "Authentication",
    description = "DTO used in the login process to validate user credentials and generate a JWT Token."
)
public record AuthTO(
    @Schema(
        description = "User email", example = "jorge@gmail.com",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "{field.null}")
    @NotEmpty(message = "{field.empty}")
    @Email(message = "{auth.email}")
    @Size(max = 60)
    @JsonProperty(value = "email")
    String email,
    
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
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{8,}$",
        message = "{user.password}"
    )
    @JsonProperty(value = "password")
    String password
) {}
