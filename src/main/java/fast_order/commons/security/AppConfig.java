package fast_order.commons.security;

import fast_order.commons.enums.APIError;
import fast_order.entity.UserEntity;
import fast_order.exception.APIRequestException;
import fast_order.repository.UserRepository;
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
    
    public AppConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Bean
    public UserDetailsService userDetailsService() {
        final String ROLE_PREFIX = "ROLE_";
        
        return (username) -> {
            Optional<UserEntity> response = userRepository.findUserByEmail(username.toLowerCase());
            
            if (response.isEmpty()) {
                APIError.RECORD_NOT_FOUND.setTitle("Usuario no encontrado");
                APIError.RECORD_NOT_FOUND.setMessage("El usuario que estas buscando no existe.");
                
                throw new APIRequestException(APIError.RECORD_NOT_FOUND);
            }
            
            UserEntity existingUser = response.get();
            String roleName = ROLE_PREFIX + existingUser.getRole().getRoleName();
            
            SimpleGrantedAuthority role = new SimpleGrantedAuthority(roleName);
            
            return User.builder()
                       .username(existingUser.getEmail())
                       .password(existingUser.getPassword())
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
