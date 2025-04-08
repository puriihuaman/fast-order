package fast_order.service;

import fast_order.dto.RoleTO;
import fast_order.dto.UserTO;
import fast_order.entity.UserEntity;
import fast_order.mapper.RoleMapper;
import fast_order.mapper.UserMapper;
import fast_order.repository.UserRepository;
import fast_order.service.use_case.UserServiceUseCase;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        return userMapper.toDTOList(userRepository.findAll());
    }
    
    @Override
    public UserTO findUserById(Long id) {
        Optional<UserEntity> user = userRepository.findById(id);
        
        if (user.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        
        return userMapper.toDTO(user.get());
    }
    
    @Override
    public UserTO findUserByEmail(String email) {
        Optional<UserEntity> user = userRepository.findByEmail(email);
        
        if (user.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        
        return userMapper.toDTO(user.get());
    }
    
    @Override
    public UserTO createUser(UserTO user) {
        UserEntity userEntity = userMapper.toEntity(user);
        
        RoleTO roleEntity = roleService.findRoleById(user.getRoleId());
        
        userEntity.setRole(roleMapper.toEntity(roleEntity));
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        
        UserEntity createdUser = userRepository.save(userEntity);
        return userMapper.toDTO(createdUser);
    }
    
    @Override
    public UserTO updateUser(Long id, UserTO user) {
        UserTO existingUser = this.findUserById(id);
        
        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        existingUser.setSignUpDate(user.getSignUpDate());
        existingUser.setTotalSpent(user.getTotalSpent());
        existingUser.setRoleId(user.getRoleId());
        
        UserEntity userToUpdate = userMapper.toEntity(existingUser);
        
        return userMapper.toDTO(userRepository.save(userToUpdate));
    }
    
    @Override
    public void deleteUser(Long id) {
        UserTO existingUser = this.findUserById(id);
        userRepository.deleteById(existingUser.getId());
    }
}
