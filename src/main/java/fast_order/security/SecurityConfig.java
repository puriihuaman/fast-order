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
            
            // Auth
            authRequest.requestMatchers("/auth/login").permitAll();
            
            // Users
            authRequest
                .requestMatchers(HttpMethod.DELETE, "/users/delete/**")
                .hasAnyRole(ROLE_ADMIN);
            authRequest.requestMatchers("/users/create", "/users/update/**").hasAnyRole(
                ROLE_ADMIN,
                ROLE_USER
            );
            authRequest.requestMatchers("/users/id/**", "/users/all").hasAnyRole(
                ROLE_ADMIN,
                ROLE_USER,
                ROLE_INVITED
            );
            
            
            // Products
            authRequest.requestMatchers(HttpMethod.DELETE, "/products/delete/**").hasAnyRole(
                ROLE_ADMIN);
            authRequest.requestMatchers(
                "/products/create",
                "/products/update/**",
                "/products/update/price/**",
                "/products/update/stock/**"
            ).hasAnyRole(ROLE_ADMIN, ROLE_USER);
            authRequest.requestMatchers("/products/id/**", "/products/all").hasAnyRole(
                ROLE_ADMIN,
                ROLE_USER,
                ROLE_INVITED
            );
            
            // Orders
            authRequest.requestMatchers(HttpMethod.DELETE, "/products/cancel/**").hasAnyRole(
                ROLE_ADMIN);
            authRequest.requestMatchers("/products/create", "/products/update/**").hasAnyRole(
                ROLE_ADMIN,
                ROLE_USER
            );
            authRequest.requestMatchers("/products/id/**", "/products/all").hasAnyRole(
                ROLE_ADMIN,
                ROLE_USER,
                ROLE_INVITED
            );
            
            authRequest.anyRequest().authenticated();
        });
        
        http.sessionManagement((session) -> {
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            
            session.maximumSessions(1);
        }).addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
}
