package fast_order.service;

import fast_order.commons.enums.APIError;
import fast_order.dto.RoleTO;
import fast_order.dto.UserTO;
import fast_order.entity.UserEntity;
import fast_order.exception.APIRequestException;
import fast_order.mapper.RoleMapper;
import fast_order.mapper.UserMapper;
import fast_order.repository.UserRepository;
import fast_order.service.use_case.UserServiceUseCase;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements UserServiceUseCase {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleService roleService;
    private final RoleMapper roleMapper;
    private final PasswordEncoder passwordEncoder;
    
    public UserService(
        UserRepository userRepository,
        UserMapper userMapper,
        RoleService roleService,
        RoleMapper roleMapper,
        PasswordEncoder passwordEncoder
    )
    {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.roleService = roleService;
        this.roleMapper = roleMapper;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    public List<UserTO> findAllUsers() {
        try {
            return userMapper.toDTOList(userRepository.findAll());
        } catch (DataAccessException ex) {
            throw new APIRequestException(APIError.DATABASE_ERROR);
        } catch (Exception ex) {
            throw new APIRequestException(APIError.INTERNAL_SERVER_ERROR);
        }
    }
    
    @Override
    public UserTO findUserById(UUID id) {
        try {
            Optional<UserEntity> response = userRepository.findById(id);
            
            if (response.isEmpty()) {
                APIError.RECORD_NOT_FOUND.setTitle("User not found");
                APIError.RECORD_NOT_FOUND.setMessage("The user you are trying to access does not exist.");
                throw new APIRequestException(APIError.RECORD_NOT_FOUND);
            }
            
            return userMapper.toDTO(response.get());
        } catch (APIRequestException ex) {
            throw ex;
        } catch (DataAccessException ex) {
            throw new APIRequestException(APIError.DATABASE_ERROR);
        } catch (Exception ex) {
            throw new APIRequestException(APIError.INTERNAL_SERVER_ERROR);
        }
    }
    
    @Override
    public UserTO findUserByEmail(String email) {
        try {
            Optional<UserEntity> response = userRepository.findUserByEmail(email);
            
            if (response.isEmpty()) {
                APIError.RECORD_NOT_FOUND.setTitle("User not found");
                APIError.RECORD_NOT_FOUND.setMessage(
                    "User not found. Please check your email.");
                throw new APIRequestException(APIError.RECORD_NOT_FOUND);
            }
            
            return userMapper.toDTO(response.get());
        } catch (APIRequestException ex) {
            throw ex;
        } catch (DataAccessException ex) {
            throw new APIRequestException(APIError.DATABASE_ERROR);
        } catch (Exception ex) {
            throw new APIRequestException(APIError.INTERNAL_SERVER_ERROR);
        }
    }
    
    @Override
    public UserTO createUser(UserTO user) {
        try {
            if (user.getRoleId() == null) {
                APIError.BAD_REQUEST.setTitle("ID is required");
                APIError.BAD_REQUEST.setMessage("The role ID is required. Please provide a valid ID.");
                throw new APIRequestException(APIError.BAD_REQUEST);
            }
            
            Optional<UserEntity> response = userRepository.findUserByEmail(user.getEmail());
            if (response.isPresent()) {
                APIError.BAD_REQUEST.setTitle("Registered email");
                APIError.BAD_REQUEST.setMessage("The email already exists. Please check your email.");
                throw new APIRequestException(APIError.BAD_REQUEST);
            }

            UserEntity userEntity = userMapper.toEntity(user);
            
            RoleTO role = roleService.findRoleById(user.getRoleId());
            
            userEntity.setRole(roleMapper.toEntity(role));
            userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
            
            UserEntity createdUser = userRepository.save(userEntity);
            return userMapper.toDTO(createdUser);
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
    
    @Override
    public UserTO updateUser(UUID id, UserTO user) {
        try {
            UserTO existingUser = this.findUserById(id);
            
            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
            existingUser.setSignUpDate(user.getSignUpDate());
            existingUser.setTotalSpent(user.getTotalSpent());
            existingUser.setRoleId(user.getRoleId());
            
            UserEntity userToUpdate = userMapper.toEntity(existingUser);
            
            return userMapper.toDTO(userRepository.save(userToUpdate));
        } catch (APIRequestException ex) {
            throw ex;
        } catch (DataIntegrityViolationException ex) {
            Throwable cause = ex.getCause();
            Throwable rootCause = cause.getCause();
            if (cause instanceof ConstraintViolationException ||
                rootCause instanceof DataIntegrityViolationException) throw new APIRequestException(
                APIError.UNIQUE_CONSTRAINT_VIOLATION);
            else throw new APIRequestException(APIError.RESOURCE_ASSOCIATED_EXCEPTION);
        } catch (DataAccessException ex) {
            throw new APIRequestException(APIError.DATABASE_ERROR);
        } catch (Exception ex) {
            throw new APIRequestException(APIError.INTERNAL_SERVER_ERROR);
        }
    }
    
    @Override
    public void deleteUser(UUID id) {
        try {
            UserTO existingUser = this.findUserById(id);
            userRepository.deleteById(existingUser.getId());
        } catch (APIRequestException ex) {
            throw ex;
        } catch (DataIntegrityViolationException ex) {
            Throwable cause = ex.getCause();
            Throwable rootCause = cause.getCause();
            if (cause instanceof ConstraintViolationException ||
                rootCause instanceof DataIntegrityViolationException) throw new APIRequestException(
                APIError.UNIQUE_CONSTRAINT_VIOLATION);
            else throw new APIRequestException(APIError.RESOURCE_ASSOCIATED_EXCEPTION);
        } catch (DataAccessException ex) {
            throw new APIRequestException(APIError.DATABASE_ERROR);
        } catch (Exception ex) {
            throw new APIRequestException(APIError.INTERNAL_SERVER_ERROR);
        }
    }
}
