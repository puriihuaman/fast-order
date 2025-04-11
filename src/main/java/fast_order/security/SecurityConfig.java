package fast_order.security;

import fast_order.enums.RoleType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtRequestFilter jwtRequestFilter;
    
    private static final String ROLE_ADMIN = RoleType.ADMIN.name();
    private static final String ROLE_USER = RoleType.USER.name();
    private static final String ROLE_INVITED = RoleType.INVITED.name();
    
    
    public SecurityConfig(final JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        
        http.authorizeHttpRequests((authRequest) -> {
            authRequest
                .requestMatchers(
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/swagger-ui.html",
                    "/swagger-resources/**"
                )
                .permitAll();
            
            authRequest.requestMatchers(HttpMethod.POST, "/auth/**").permitAll();
            authRequest.requestMatchers("/users/**").permitAll();
            
            // Roles
            authRequest.requestMatchers(HttpMethod.GET, "/users/**").permitAll();
            authRequest.requestMatchers(HttpMethod.POST, "/users/**").hasAnyRole(
                ROLE_ADMIN,
                ROLE_USER
            );
            authRequest.requestMatchers(HttpMethod.PUT, "/users/**").hasAnyRole(
                ROLE_ADMIN,
                ROLE_USER
            );
            authRequest.requestMatchers(HttpMethod.DELETE, "/users/**").hasAnyRole(ROLE_ADMIN);
            
            // Products
            authRequest.requestMatchers(HttpMethod.GET, "/products/**").permitAll();
            authRequest.requestMatchers(HttpMethod.POST, "/products/**").hasAnyRole(
                ROLE_ADMIN,
                ROLE_USER
            );
            authRequest.requestMatchers(HttpMethod.PUT, "/products/**").hasAnyRole(ROLE_ADMIN);
            authRequest.requestMatchers(HttpMethod.DELETE, "/products/**").hasAnyRole(ROLE_ADMIN);
            
            authRequest.anyRequest().authenticated();
        });
        
        http.sessionManagement((session) -> {
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            
            session.maximumSessions(1);
        }).addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
}
