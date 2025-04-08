package fast_order.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
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
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class UserTO {
    @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
    private Long id;
    
    @NotNull
    @NotEmpty
    @Size(min = 2)
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]*")
    @JsonProperty(value = "name")
    private String name;
    
    @NotNull
    @Email
    @JsonProperty(value = "email")
    private String email;
    
    @NotNull
    @NotEmpty
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{8,}$")
    @JsonProperty(value = "password")
    private String password;
    
    @NotNull
    @JsonProperty(value = "signUpDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate signUpDate;
    
    @NotNull
    @Min(value = 0)
    @PositiveOrZero
    @JsonProperty(value = "totalSpent")
    private Double totalSpent;

    @NotNull(message = "The role is required")
    @JsonProperty(value = "roleId")
    private Long roleId;
}
