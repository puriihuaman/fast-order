package fast_order.controller;

import fast_order.dto.RoleTO;
import fast_order.enums.APISuccess;
import fast_order.enums.RoleType;
import fast_order.service.RoleService;
import fast_order.utils.APIResponseData;
import fast_order.utils.APIResponseHandler;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/roles", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class RoleController {
    private final RoleService roleService;
    
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }
    
    @GetMapping("all")
    public ResponseEntity<APIResponseData> findAllRoles() {
        List<RoleTO> roles = roleService.findRoles();
        return APIResponseHandler.handleResponse(APISuccess.RESOURCE_RETRIEVED, roles);
    }
    
    @GetMapping("id/{id}")
    public ResponseEntity<APIResponseData> findRoleById(@PathVariable("id") Long id) {
        RoleTO role = roleService.findRoleById(id);
        return APIResponseHandler.handleResponse(APISuccess.RESOURCE_RETRIEVED, role);
    }
    
    @GetMapping("role/{name}")
    public ResponseEntity<APIResponseData> findRoleByRoleName(@PathVariable("name") RoleType name) {
        RoleTO role = roleService.findRoleByRoleName(name);
        return APIResponseHandler.handleResponse(APISuccess.RESOURCE_RETRIEVED, role);
    }
    
    @PostMapping("create")
    public ResponseEntity<APIResponseData> createRole(@Valid @RequestBody RoleTO role) {
        RoleTO savedRole = roleService.createRole(role);
        return APIResponseHandler.handleResponse(APISuccess.RESOURCE_CREATED, savedRole);
    }
}




























