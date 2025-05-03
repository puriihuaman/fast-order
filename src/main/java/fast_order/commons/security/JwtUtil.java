package fast_order.commons.security;

import fast_order.dto.RoleTO;
import fast_order.dto.UserTO;
import fast_order.commons.enums.APIError;
import fast_order.exception.APIRequestException;
import fast_order.service.RoleService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

@Component
public class JwtUtil {
    @Value("${jwt.secret.key}")
    private String SECRET_KEY;
    
    @Value("${jwt.time.expiration}")
    private Long TIME_EXPIRATION;
    
    private final RoleService roleService;
    
    public JwtUtil(RoleService roleService) {
        this.roleService = roleService;
    }
    
    public String extractUsername(String token) {
        if (token == null || token.isEmpty()) {
            throw new APIRequestException(
                APIError.UNAUTHORIZED,
                                          "Token requerido",
                                          "El token esta vacío."
            );
        }
        
        return this.extractClaim(token, Claims::getSubject);
    }
    
    public Date extractExpiration(String token) {
        return this.extractClaim(token, Claims::getExpiration);
    }
    
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = this.extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                   .verifyWith(this.getKey())
                   .build()
                   .parseSignedClaims(token)
                   .getPayload();
    }
    
    public boolean isTokenExpired(String token) {
        return this.extractExpiration(token).before(new Date());
    }
    
    public String generateToken(UserTO userTO) {
        final String ROLE_PREFIX = "ROLE_";
        String roleName = this.getRoleById(userTO.getRoleId());
        
        Map<String, Object> claims = new HashMap<>();
        claims.put("name", userTO.getName());
        claims.put("email", userTO.getEmail());
        claims.put("role", ROLE_PREFIX + roleName);
        return this.buildToken(claims, userTO.getEmail());
    }
    
    private String buildToken(Map<String, Object> claims, String subject) {
        Date time = new Date(System.currentTimeMillis());
        Date expiration = new Date(System.currentTimeMillis() + TIME_EXPIRATION);
        
        return Jwts.builder()
                   .claims(claims)
                   .subject(subject)
                   .issuedAt(time)
                   .expiration(expiration)
                   .signWith(this.getKey(), Jwts.SIG.HS256)
                   .compact();
    }
    
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = this.extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !this.isTokenExpired(token));
    }
    
    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    
    public String getRoleById(UUID id) {
        RoleTO role = roleService.findRoleById(id);
        return role.getRoleName().name();
    }
}
