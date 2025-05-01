package fast_order.commons.security;

import fast_order.commons.enums.APIError;
import fast_order.dto.RoleTO;
import fast_order.dto.UserTO;
import fast_order.entity.UserEntity;
import fast_order.exception.APIRequestException;
import fast_order.mapper.UserMapper;
import fast_order.repository.UserRepository;
import fast_order.service.RoleService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Configuration
public class AppConfig {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final UserMapper userMapper;
    
    public AppConfig(UserRepository userRepository, RoleService roleService, UserMapper userMapper)
    {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.userMapper = userMapper;
    }
    
    @Bean
    public UserDetailsService userDetailsService() {
        final String ROLE_PREFIX = "ROLE_";
        
        return (username) -> {
            Optional<UserEntity>
                existingUser =
                userRepository.findUserByEmail(username.toLowerCase());
            
            if (existingUser.isEmpty()) {
                APIError.RECORD_NOT_FOUND.setTitle("Usuario no encontrado");
                APIError.RECORD_NOT_FOUND.setMessage("El usuario que estas buscando no existe.");
                
                throw new APIRequestException(APIError.RECORD_NOT_FOUND);
            }
            
            UserTO user = userMapper.toDTO(existingUser.get());
            RoleTO roleDTO = roleService.findRoleById(user.getRoleId());
            String roleName = ROLE_PREFIX + roleDTO.getRoleName();
            
            SimpleGrantedAuthority role = new SimpleGrantedAuthority(roleName);
            
            return User.builder()
                       .username(user.getEmail())
                       .password(user.getPassword())
                       .authorities(role)
                       .build();
        };
    }
    
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthProvider = new DaoAuthenticationProvider();
        daoAuthProvider.setUserDetailsService(this.userDetailsService());
        daoAuthProvider.setPasswordEncoder(this.passwordEncoder());
        return daoAuthProvider;
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig)
    throws Exception
    {
        return authConfig.getAuthenticationManager();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
