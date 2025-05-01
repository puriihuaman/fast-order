package fast_order.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
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

@Entity(name = "USER")
@Table(name = "USERS", schema = "fast_order_schema")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", unique = true)
    private Long id;
    
    @NotNull(message = "{field.null}")
    @NotEmpty(message = "{field.empty}")
    @Size(min = 2, message = "{user.name.size}")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]*", message = "{user.name.pattern}")
    @Column(name = "name", nullable = false, length = 80)
    private String name;
    
    @NotNull(message = "{field.null}")
    @Email(message = "{user.email}")
    @Column(name = "email", nullable = false, unique = true, length = 60)
    private String email;
    
    @NotNull(message = "{field.null}")
    @NotEmpty(message = "{field.empty}")
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{8,}$", message = "{user.password}"
    )
    @Column(name = "password", nullable = false, length = 100)
    private String password;
    
    @NotNull(message = "{field.null}")
    @FutureOrPresent(message = "{user.signUpDate}")
    @Column(name = "sign_up_date", nullable = false)
    private LocalDate signUpDate;
    
    @NotNull(message = "{field.null}")
    @DecimalMin(value = "0.0", message = "{user.min.totalSpent}")
    @PositiveOrZero(message = "{user.totalSpent.positive}")
    @Column(name = "total_spent", nullable = false)
    private Double totalSpent;
    
    @Valid
    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "role_id", referencedColumnName = "role_id", nullable = false)
    private RoleEntity role;
}
