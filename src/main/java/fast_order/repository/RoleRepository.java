package fast_order.repository;

import fast_order.entity.RoleEntity;
import fast_order.commons.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findRoleByRoleName(RoleType name);
}
