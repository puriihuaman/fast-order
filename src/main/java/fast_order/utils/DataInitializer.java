package fast_order.security;

import fast_order.dto.RoleTO;
import fast_order.dto.UserTO;
import fast_order.enums.RoleType;
import fast_order.mapper.RoleMapper;
import fast_order.service.RoleService;
import fast_order.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;

@Component
public class DataInitializer {
    private final UserService userService;
    private final RoleService roleService;
    private final RoleMapper roleMapper;
    
    public DataInitializer(UserService userService, RoleService roleService, RoleMapper roleMapper)
    {
        this.userService = userService;
        this.roleService = roleService;
        this.roleMapper = roleMapper;
    }
    
    @PostConstruct
    @Transactional
    public void init() {
        this.createRoles();
        //        this.createAdminUser();
    }
    
    private void createRoles() {
        Arrays.stream(RoleType.values()).forEach((role) -> {
            RoleTO roleDTO = RoleTO
                .builder()
                .roleName(role)
                .description("Role " + role.name())
                .build();
            
            RoleTO created = roleService.createRole(roleDTO);
            System.out.println("************* ROL ************");
            System.out.println(created.getId());
            System.out.println(created.getRoleName());
            System.out.println(created.getDescription());
            System.out.println("************* ROL ************");
        });
    }
    
    private void createAdminUser() {
        try {
            RoleTO
                role =
                RoleTO.builder().roleName(RoleType.ADMIN).description("Role admin").build();
            RoleTO adminRole = roleService.createRole(role);
            System.out.println("********************************");
            System.out.println("********** Rol creado");
            System.out.println(adminRole.getId());
            System.out.println(adminRole.getRoleName());
            System.out.println(adminRole.getDescription());
            System.out.println("********************************");
            
            final String ENCODED_PASSWORD = "AdM¡N&20_25";
            
            UserTO user = UserTO
                .builder()
                .name("Bytes Colaborativos")
                .email("bytes@colaborativos.es")
                .password(ENCODED_PASSWORD)
                .signUpDate(LocalDate.now())
                .totalSpent(0.0)
                .roleId(adminRole.getId())
                .build();
            System.out.println(user.getRoleId());
            userService.createUser(user);
        } catch (DataAccessException ex) {
            throw new RuntimeException("Error database accesses");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
