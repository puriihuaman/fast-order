package fast_order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import fast_order.enums.RoleType;
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

@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@Setter
public class RoleTO {
    @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
    private Long id;
    
    @NotNull(message = "{field.null}")
    @Enumerated(EnumType.STRING)
    @JsonProperty(value = "roleName")
    private RoleType roleName;
    
    @NotNull(message = "{field.null}")
    @NotEmpty(message = "{field.empty}")
    @Size(min = 5, message = "{role.description.size}")
    @JsonProperty(value = "description")
    private String description;
}
