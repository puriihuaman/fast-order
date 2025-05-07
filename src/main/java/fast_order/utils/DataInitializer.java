package fast_order.utils;

import fast_order.commons.enums.APIError;
import fast_order.commons.enums.RoleType;
import fast_order.dto.RoleTO;
import fast_order.dto.UserTO;
import fast_order.exception.APIRequestException;
import fast_order.service.DataInitializerService;
import fast_order.service.RoleService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

/**
 * Component for initializing data essential for the operation of the system.
 * *
 * Runs initialization scripts at application startup to ensure the existence of:
 * - Base roles of the system ({@link RoleType})
 * - Initial admin user
 * *
 * Data is only created if it does not previously exist in the database.
 * *
 * Current implementation
 * - Single execution after Spring container initialization.
 * - Transactionality at the full method level
 * - Hierarchical exception handing
 */
@Component
public class DataInitializer {
    private final RoleService roleService;
    private final DataInitializerService dataInitializerService;
    
    @Value("${DEFAULT_USER_PASSWORD}")
    private String USER_PASSWORD;
    @Value("${DEFAULT_USER_EMAIL}")
    private String USER_EMAIL;
    
    public DataInitializer(RoleService roleService, DataInitializerService dataInitializerService) {
        this.roleService = roleService;
        this.dataInitializerService = dataInitializerService;
    }
    
    /**
     * Main initialization method.
     * *
     * Execution sequence:
     * - Creating base roles
     * - Administrator user creation
     * *
     * {@code @PostConstruct} for execution after bean initialization
     * {@code @Transactional} ensures that the entire operation is executed in a single transaction
     */
    @PostConstruct
    @Transactional
    public void init() {
        this.createRoles();
        this.createAdminUser();
    }
    
    /**
     * Creates the roles defined in {@link RoleType} with predefined descriptions.
     * *
     * Descriptions are assigned based on the role type:
     * - ADMIN: Full access
     * - USER: Limited access
     * - INVITED: Restricted access
     */
    private void createRoles() {
        Arrays.stream(RoleType.values()).forEach((role) -> {
            String adminDescription = "Full system access";
            String userDescription = "Limited access to your permissions";
            String invitedDescription = "Restricted access to your permissions";
            
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
    
    /**
     * Creates the initial system administrator user.
     */
    private void createAdminUser() {
        try {
            String name = "Bytes Colaborativos";
            String email = USER_EMAIL;
            String password = USER_PASSWORD;
            
            UserTO userCreated = dataInitializerService.createInitialUser(
                name,
                email,
                password,
                RoleType.ADMIN
            );
            this.logCreationDetails(userCreated);
        } catch (APIRequestException ex) {
            throw ex;
        } catch (DataAccessException ex) {
            throw new APIRequestException(APIError.DATABASE_ERROR);
        } catch (Exception ex) {
            throw new APIRequestException(APIError.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Records the details of the created user in the console.
     *
     * @param user Persistent user
     */
    private void logCreationDetails(UserTO user) {
        System.out.println("--------- START: CREATING USER ----------");
        System.out.println(user.getId());
        System.out.println(user.getEmail());
        System.out.println("--------- END: USER CREATED ----------");
    }
}
