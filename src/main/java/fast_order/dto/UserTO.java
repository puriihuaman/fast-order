package fast_order.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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

import java.time.LocalDate;
import java.util.UUID;

/**
 * Data Transfer Object (DTO) that represents a user registered in the system.
 * -
 * Contains information about the user, including their email address, relationships with other
 * entities, and validation restrictions.
 * It is primarily used for registration and user management.
 * -
 * Lombok annotations ({@code @Builder}, {@code @Getter}, {@code @Setter}) automatically generate
 * the builder pattern, access methods, and modification methods.
 * Validations ensure compliance with security policies and data formats.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(name = "User", description = "DTO that represents a user registered in the system.")
public class UserTO {
    @Schema(
        description = "Unique user ID.", example = "1", accessMode = Schema.AccessMode.READ_ONLY,
        hidden = true
    )
    @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
    private UUID id;
    
    @Schema(
        description = "User's full name. Letters and spaces are allowed. Minimum 2 characters.",
        example = "Jorge Suarez", requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotEmpty(message = "{field.empty}")
    @Size(min = 2, message = "{user.name.size}")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+", message = "{user.name.pattern}")
    @JsonProperty(value = "name")
    private String name;
    
    @Schema(
        description = "User email", example = "jorge@gmail.com",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotEmpty(message = "{field.empty}")
    @Email(message = "{user.email}")
    @JsonProperty(value = "email")
    private String email;
    
    @Schema(
        description = """
                      User password. Must contain uppercase and lowercase letters, numbers,
                      and special characters.
                      Minimum 8 characters.
                      """, example = "J0rG3#B&t3s", requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotEmpty(message = "{field.empty}")
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{8,}$", message = "{user.password}"
    )
    @JsonProperty(value = "password")
    private String password;
    
    @Schema(description = "User registration date. Must be today", example = "2025-04-12")
    @JsonProperty(value = "signUpDate", defaultValue = "TODAY")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate signUpDate;
    
    @Schema(description = "Total amount of user spending. Cannot be negative.", example = "150.50")
    @JsonProperty(value = "totalSpent")
    private Double totalSpent;
    
    @Schema(
        description = "ID of the assigned to the user. Relates to the Role entity", example = "2"
    )
    @NotNull(message = "{user.roleId}")
    @JsonProperty(value = "roleId")
    private UUID roleId;
}
