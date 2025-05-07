package fast_order.service;

import fast_order.commons.enums.APIError;
import fast_order.commons.enums.RoleType;
import fast_order.dto.RoleTO;
import fast_order.entity.RoleEntity;
import fast_order.exception.APIRequestException;
import fast_order.mapper.RoleMapper;
import fast_order.repository.RoleRepository;
import fast_order.service.use_case.RoleServiceUseCase;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RoleService implements RoleServiceUseCase {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    
    public RoleService(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }
    
    @Override
    public List<RoleTO> findRoles() {
        try {
            return roleMapper.toDTOList(roleRepository.findAll());
        } catch (DataAccessException ex) {
            throw new APIRequestException(APIError.DATABASE_ERROR);
        } catch (Exception ex) {
            throw new APIRequestException(APIError.INTERNAL_SERVER_ERROR);
        }
    }
    
    @Override
    public RoleTO findRoleById(UUID id) {
        try {
            Optional<RoleEntity> response = roleRepository.findById(id);
            
            if (response.isEmpty()) {
                APIError.RECORD_NOT_FOUND.setTitle("Role not found");
                APIError.RECORD_NOT_FOUND.setMessage(
                    "The role you are trying to access does not exist in the system.");
                throw new APIRequestException(APIError.RECORD_NOT_FOUND);
            }
            
            return roleMapper.toDTO(response.get());
        } catch (APIRequestException ex) {
            throw ex;
        } catch (DataAccessException ex) {
            throw new APIRequestException(APIError.DATABASE_ERROR);
        } catch (Exception ex) {
            throw new APIRequestException(APIError.INTERNAL_SERVER_ERROR);
        }
    }
    
    @Override
    public RoleTO findRoleByRoleName(RoleType roleName) {
        try {
            Optional<RoleEntity> response = roleRepository.findRoleByRoleName(roleName);
            
            if (response.isEmpty()) {
                APIError.RECORD_NOT_FOUND.setTitle("Role not found");
                APIError.RECORD_NOT_FOUND.setMessage(
                    "The role name you are trying to access does not exist in the system.");
                throw new APIRequestException(APIError.RECORD_NOT_FOUND);
            }
            
            return roleMapper.toDTO(response.get());
        } catch (APIRequestException ex) {
            throw ex;
        } catch (DataAccessException ex) {
            throw new APIRequestException(APIError.DATABASE_ERROR);
        } catch (Exception ex) {
            throw new APIRequestException(APIError.INTERNAL_SERVER_ERROR);
        }
    }
    
    @Override
    public RoleTO createRole(RoleTO role) {
        try {
            Optional<RoleEntity>
                existingRole =
                roleRepository.findRoleByRoleName(role.getRoleName());
            
            if (existingRole.isEmpty()) {
                RoleEntity roleEntity = roleRepository.save(roleMapper.toEntity(role));
                return roleMapper.toDTO(roleEntity);
            } else {
                return roleMapper.toDTO(existingRole.get());
            }
        } catch (APIRequestException ex) {
            throw ex;
        } catch (DataIntegrityViolationException ex) {
            Throwable cause = ex.getCause();
            Throwable rootCause = cause.getCause();
            if (cause instanceof ConstraintViolationException ||
                rootCause instanceof ConstraintViolationException) throw new APIRequestException(
                APIError.UNIQUE_CONSTRAINT_VIOLATION);
            else throw new APIRequestException(APIError.RESOURCE_ASSOCIATED_EXCEPTION);
            
        } catch (DataAccessException ex) {
            throw new APIRequestException(APIError.DATABASE_ERROR);
        } catch (Exception ex) {
            throw new APIRequestException(APIError.INTERNAL_SERVER_ERROR);
        }
    }
}
