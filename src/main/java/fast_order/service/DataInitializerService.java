package fast_order.service;

import fast_order.commons.enums.APIError;
import fast_order.commons.enums.RoleType;
import fast_order.dto.RoleTO;
import fast_order.dto.UserTO;
import fast_order.entity.UserEntity;
import fast_order.exception.APIRequestException;
import fast_order.mapper.UserMapper;
import fast_order.repository.UserRepository;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("dataInitializerService")
public class DataInitializerService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    
    public DataInitializerService(
        UserRepository userRepository,
        UserMapper userMapper,
        RoleService roleService,
        PasswordEncoder passwordEncoder
    )
    {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }
    
    public UserTO createInitialUser(String name, String email, String password, RoleType roleName) {
        try {
            Optional<UserEntity> response = userRepository.findUserByEmail(email);
            
            if (response.isPresent()) {
                return userMapper.toDTO(response.get());
            }
            
            RoleTO existingRole = roleService.findRoleByRoleName(roleName);
            
            UserTO user = UserTO.builder().name(name).email(email).password(passwordEncoder.encode(
                password)).roleId(existingRole.getId()).build();
            
            return userMapper.toDTO(userRepository.save(userMapper.toEntity(user)));
        } catch (APIRequestException ex) {
            throw ex;
        } catch (DataIntegrityViolationException ex) {
            Throwable cause = ex.getCause();
            Throwable rootCause = cause.getCause();
            if (rootCause instanceof ConstraintViolationException ||
                rootCause instanceof DataIntegrityViolationException)
            {
                throw new APIRequestException(APIError.UNIQUE_CONSTRAINT_VIOLATION);
            } else throw new APIRequestException(APIError.RESOURCE_ASSOCIATED_EXCEPTION);
        } catch (DataAccessException ex) {
            throw new APIRequestException(APIError.DATABASE_ERROR);
        } catch (Exception ex) {
            throw new APIRequestException(APIError.INTERNAL_SERVER_ERROR);
        }
    }
}
