package fast_order.service.use_case;

import fast_order.dto.RoleTO;
import fast_order.commons.enums.RoleType;

import java.util.List;
import java.util.UUID;

public interface RoleServiceUseCase {
    List<RoleTO> findRoles();
    
    RoleTO findRoleById(UUID id);

    RoleTO findRoleByRoleName(RoleType roleName);
    
    RoleTO createRole(RoleTO role);
}
