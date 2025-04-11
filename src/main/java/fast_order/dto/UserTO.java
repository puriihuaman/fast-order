package fast_order.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@Setter
public class UserTO {
    @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
    private Long id;
    
    @NotNull(message = "{field.null}")
    @NotEmpty(message = "{field.empty}")
    @Size(min = 2, message = "{user.name.size}")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]*", message = "{user.name.pattern}")
    @JsonProperty(value = "name")
    private String name;
    
    @NotNull(message = "{field.null}")
    @Email(message = "{user.email}")
    @JsonProperty(value = "email")
    private String email;
    
    @NotNull(message = "{field.null}")
    @NotEmpty(message = "{field.empty}")
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{8,}$", message = "{user.password}"
    )
    @JsonProperty(value = "password")
    private String password;
    
    @NotNull(message = "{field.null}")
    @FutureOrPresent(message = "{user.signUpDate}")
    @JsonProperty(value = "signUpDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate signUpDate;
    
    @NotNull(message = "{field.null}")
    @DecimalMin(value = "0.0", message = "{user.min.totalSpent}")
    @PositiveOrZero(message = "{user.totalSpent.positive}")
    @JsonProperty(value = "totalSpent")
    private Double totalSpent;
    
    @NotNull(message = "{user.roleId}")
    @JsonProperty(value = "roleId")
    private Long roleId;
}
