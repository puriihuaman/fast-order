package fast_order.commons.security;

import fast_order.dto.AuthTO;
import fast_order.dto.TokenResponseTO;
import fast_order.dto.UserTO;
import fast_order.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {
    private final AuthenticationManager authManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;
    
    public SecurityService(
        AuthenticationManager authManager,
        UserService userService,
        JwtUtil jwtUtil
    )
    {
        this.authManager = authManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }
    
    public TokenResponseTO authenticate(AuthTO auth) {
        try {
            UsernamePasswordAuthenticationToken
                authRequest =
                new UsernamePasswordAuthenticationToken(auth.email(), auth.password());
            Authentication authResult = authManager.authenticate(authRequest);
            
            UserDetails userDetails = (UserDetails) authResult.getPrincipal();
            UserTO existingUser = userService.findUserByEmail(userDetails.getUsername());
            
            String token = jwtUtil.generateToken(existingUser);
            String role = jwtUtil.getRoleById(existingUser.getRoleId());
            
            return new TokenResponseTO(token, existingUser.getName(), role);
        } catch (BadCredentialsException ex) {
            throw new BadCredentialsException(ex.getMessage());
        } catch (Exception ex) {
            throw new InternalError(ex.getMessage());
        }
    }
}
