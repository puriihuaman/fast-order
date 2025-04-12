package fast_order.utils;

import fast_order.dto.RoleTO;
import fast_order.dto.UserTO;
import fast_order.enums.APIError;
import fast_order.enums.RoleType;
import fast_order.exception.APIRequestException;
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
    
    public DataInitializer(UserService userService, RoleService roleService)
    {
        this.userService = userService;
        this.roleService = roleService;
    }
    
    @PostConstruct
    @Transactional
    public void init() {
        this.createRoles();
        this.createAdminUser();
    }
    
    private void createRoles() {
        Arrays.stream(RoleType.values()).forEach((role) -> {
            String adminDescription = "Acceso total al sistema.";
            String userDescription = "Acceso limitado a sus permisos.";
            String invitedDescription = "Acceso restringido.";
            
            RoleTO
                roleToCreate =
                RoleTO.builder().roleName(role).description(role.equals(RoleType.ADMIN)
                                                                ? adminDescription
                                                                : role.equals(RoleType.USER)
                                                                    ? userDescription
                                                                    : invitedDescription).build();
            
            roleService.createRole(roleToCreate);
        });
    }
    
    private void createAdminUser() {
        try {
            RoleTO
                role =
                RoleTO.builder().roleName(RoleType.ADMIN).description("Role admin").build();
            RoleTO createdRole = roleService.createRole(role);
            
            final String ENCODED_PASSWORD = "AdM¡N&20_25";
            
            UserTO user = UserTO
                .builder()
                .name("Bytes Colaborativos")
                .email("bytes@colaborativos.es")
                .password(ENCODED_PASSWORD)
                .signUpDate(LocalDate.now())
                .totalSpent(0.0)
                .roleId(createdRole.getId())
                .build();
            
            UserTO userCreated = userService.createUser(user);
            System.out.println("--------- USER CREATED ----------");
            System.out.println(userCreated.getId());
            System.out.println(userCreated.getEmail());
            System.out.println("--------- USER CREATED ----------");
        } catch (APIRequestException ex) {
            throw ex;
        } catch (DataAccessException ex) {
            throw new APIRequestException(APIError.DATABASE_ERROR);
        } catch (Exception ex) {
            throw new APIRequestException(APIError.INTERNAL_SERVER_ERROR);
        }
    }
}
