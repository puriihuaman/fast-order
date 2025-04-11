package fast_order.controller;

import fast_order.dto.AuthTO;
import fast_order.security.SecurityService;
import fast_order.security.TokenResponse;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class AuthController {
    private final SecurityService securityService;
    
    public AuthController(final SecurityService securityService) {
        this.securityService = securityService;
    }
    
    @PostMapping("login")
    public ResponseEntity<TokenResponse> authenticate(@Valid @RequestBody AuthTO auth) {
        TokenResponse tokenResponse = securityService.authenticate(auth);
        return ResponseEntity.ok(tokenResponse);
    }
}
