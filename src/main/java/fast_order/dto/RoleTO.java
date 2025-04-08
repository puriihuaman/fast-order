package fast_order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import fast_order.enums.RoleType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class RoleTO {
    @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
    private Long id;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    @JsonProperty(value = "roleName")
    private RoleType roleName;
    
    @NotNull
    @NotEmpty
    @JsonProperty(value = "description")
    private String description;
}
