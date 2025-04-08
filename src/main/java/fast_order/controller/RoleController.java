package fast_order.controller;

import fast_order.dto.RoleTO;
import fast_order.enums.RoleType;
import fast_order.service.RoleService;
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

@RestController
@RequestMapping(value = "/roles", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class RoleController {
    private final RoleService roleService;
    
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }
    
    @GetMapping("all")
    public ResponseEntity<Object> findAllRoles() {
        return ResponseEntity.ok(roleService.findRoles());
    }
    
    @GetMapping("id/{id}")
    public ResponseEntity<Object> findRoleById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(roleService.findRoleById(id));
    }
    
    @GetMapping("role/{name}")
    public ResponseEntity<Object> findRoleByRoleName(@PathVariable("name") RoleType name) {
        return ResponseEntity.ok(roleService.findRoleByRoleName(name));
    }
    
    @PostMapping("create")
    public ResponseEntity<Object> createRole(@Valid @RequestBody RoleTO role) {
        return ResponseEntity.ok(roleService.createRole(role));
    }
}




























