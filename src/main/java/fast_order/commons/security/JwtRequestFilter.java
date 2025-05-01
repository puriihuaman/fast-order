package fast_order.commons.security;

import fast_order.commons.enums.APIError;
import fast_order.exception.APIRequestException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    
    public JwtRequestFilter(UserDetailsService userDetailsService, JwtUtil jwtUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }
    
    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull FilterChain filterChain
    ) throws ServletException, IOException
    {
        if (this.isAuthPath(request)) {
            filterChain.doFilter(request, response);
            return;
        }
        
        String token = null;
        String email = null;
        
        final String BEARER_PREFIX = "Bearer ";
        final String AUTHORIZATION_HEADER = request.getHeader(HttpHeaders.AUTHORIZATION);
        
        if (AUTHORIZATION_HEADER != null && AUTHORIZATION_HEADER.startsWith(BEARER_PREFIX)) {
            token = AUTHORIZATION_HEADER.substring(BEARER_PREFIX.length());
            email = jwtUtil.extractUsername(token);
        }
        
        try {
            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                
                if (jwtUtil.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken
                        auth =
                        new UsernamePasswordAuthenticationToken(
                            userDetails,
                                                                null,
                                                                userDetails.getAuthorities()
                        );
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                } else {
                    throw new APIRequestException(APIError.UNAUTHORIZED);
                }
            }
            filterChain.doFilter(request, response);
        } catch (APIRequestException ex) {
            throw new APIRequestException(APIError.UNAUTHORIZED);
        }
    }
    
    private boolean isAuthPath(HttpServletRequest request) {
        String servletPath = request.getServletPath();
        return servletPath.contains("/auth/login") ||
            servletPath.startsWith("/swagger-ui") ||
            servletPath.startsWith("/v3/api-docs/");
    }
}
