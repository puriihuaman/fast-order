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
import lombok.Data;

@Entity(name = "ROLE")
@Table(name = "ROLES", schema = "fast_order_schema")
@Data
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id", unique = true)
    private Long id;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "role_name", unique = true, nullable = false, length = 35)
    private RoleType roleName;
    
    @NotNull
    @NotEmpty
    @Column(name = "description", nullable = false, length = 100)
    private String description;
}
