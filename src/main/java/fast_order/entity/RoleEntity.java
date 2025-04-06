package fast_order.entity;

import fast_order.enums.RoleType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity(name = "ROLE")
@Table(name = "ROLES", schema = "fast_order_schema")
@Data
public class RoleEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "role_id")
	private Long id;

	@NotNull
	@NotEmpty
	@Size(min = 2)
	@Enumerated(EnumType.STRING)
	@Column(name = "name", nullable = false, unique = true, length = 25)
	private RoleType name;

	@NotNull
	@NotEmpty
	@Column(name = "description", nullable = false, length = 100)
	private String description;
}
