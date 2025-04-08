package fast_order.service;

import fast_order.dto.RoleTO;
import fast_order.entity.RoleEntity;
import fast_order.enums.RoleType;
import fast_order.mapper.RoleMapper;
import fast_order.repository.RoleRepository;
import fast_order.service.use_case.RoleServiceUseCase;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        return roleMapper.toDTOList(roleRepository.findAll());
    }
    
    @Override
    public RoleTO findRoleById(Long id) {
        Optional<RoleEntity> roleEntity = roleRepository.findById(id);
        
        if (roleEntity.isEmpty()) {
            throw new RuntimeException("Role not found");
        }
        return roleMapper.toDTO(roleEntity.get());
    }
    
    @Override
    public RoleTO findRoleByRoleName(RoleType roleName) {
        Optional<RoleEntity> existingRole = roleRepository.findRoleByRoleName(roleName);
        
        if (existingRole.isEmpty()) {
            throw new RuntimeException("Role not found");
        }
        
        return roleMapper.toDTO(existingRole.get());
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
        } catch (DataIntegrityViolationException ex) {
            Throwable cause = ex.getCause();
            Throwable rootCause = cause.getCause();
            if (cause instanceof ConstraintViolationException ||
                rootCause instanceof ConstraintViolationException) throw new RuntimeException(
                "UNIQUE_CONSTRAINT_VIOLATION");
            else throw new RuntimeException("RESOURCE_ASSOCIATED_EXCEPTION");
            
        } catch (DataAccessException ex) {
            throw new RuntimeException("Error access database");
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}
