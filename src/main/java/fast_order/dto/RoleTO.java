package fast_order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import fast_order.commons.enums.RoleType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * Data Transfer Object (DTO) that plays a role in the authentication and authorization system.
 * -
 * Defines the permissions and privileges associated with users.
 * It is used in role management and permission assignment operations within the system.
 * -
 * Lombok annotations ({@code @Builder}, {@code @Getter}, {@code @Setter}) automatically generate
 * the builder pattern, accessor methods, and modifier methods.
 * Validations ensure data integrity in CRUD operations.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(
    name = "Role",
    description = """
                  A DTO that represents a role within the authentication and authorization system.
                  It is used to define permissions associated with users.
                  """
)
public class RoleTO {
    @Schema(
        description = "Unique role ID", example = "1", accessMode = Schema.AccessMode.READ_ONLY,
        hidden = true
    )
    @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
    private UUID id;
    
    @Schema(
        description = "Role name.", examples = {"ADMIN", "USER", "INVITED"},
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "{field.null}")
    @Enumerated(EnumType.STRING)
    @JsonProperty(value = "roleName")
    private RoleType roleName;
    
    @Schema(
        description = "Role description.", example = "Full system access",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotEmpty(message = "{field.empty}")
    @Size(min = 5, message = "{role.description.size}")
    @JsonProperty(value = "description")
    private String description;
}
