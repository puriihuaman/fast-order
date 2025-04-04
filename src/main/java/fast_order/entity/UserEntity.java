package fast_order.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Entity(name = "USER")
@Table(name = "USERS", schema = "fast_order_schema")
@Data
public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long id;

	@NotNull
	@NotEmpty
	@Size(min = 2)
	@Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]*")
	@Column(name = "name", nullable = false, length = 80)
	private String name;

	@NotNull
	@Email
	@Column(name = "email", nullable = false, unique = true, length = 60)
	private String email;

	@NotNull
	@NotEmpty
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{8,}$")
	@Column(name = "password", nullable = false, length = 100)
	private String password;

	@NotNull
	@Column(name = "sign_up_date", nullable = false)
	private LocalDate signUpDate;

	@NotNull
	@NotEmpty
	@Min(value = 0)
	@PositiveOrZero
	@Column(name = "total_spent", nullable = false)
	private Double totalSpent;

	@OneToOne
	@JoinColumn(name = "role_id", referencedColumnName = "role_id")
	private RoleEntity role;
}
