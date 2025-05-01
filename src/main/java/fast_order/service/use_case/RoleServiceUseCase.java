package fast_order.service.use_case;

import fast_order.dto.RoleTO;
import fast_order.commons.enums.RoleType;

import java.util.List;

public interface RoleServiceUseCase {
    List<RoleTO> findRoles();
    
    RoleTO findRoleById(Long id);
    
    RoleTO findRoleByRoleName(RoleType roleName);
    
    RoleTO createRole(RoleTO role);
}
