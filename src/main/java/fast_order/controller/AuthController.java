package fast_order.controller;

import fast_order.dto.AuthTO;
import fast_order.dto.UserTO;
import fast_order.enums.APISuccess;
import fast_order.security.SecurityService;
import fast_order.security.TokenResponse;
import fast_order.service.UserService;
import fast_order.utils.APIResponseData;
import fast_order.utils.APIResponseHandler;
import fast_order.annotation.SwaggerApiResponses;
import fast_order.utils.SwaggerResponseExample;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for user authentication and registration operations.
 * *
 * Exposes endpoints for:
 * - Generating JWT tokens using valid credentials.
 * - Registering new users in the system.
 * *
 * All responses follow the standard format defined in {@link APIResponseData}.
 * *
 * @see SecurityService JWT Authentication Service.
 * @see UserService User management service.
 */
@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@Tag(
    name = "Autenticación",
    description = """
                  Endpoints responsable for logging in and generating JWT tokens for
                  authenticated access to the system.
                  """
)
public class AuthController {
    private final SecurityService securityService;
    private final UserService userService;
    
    public AuthController(final SecurityService securityService, UserService userService) {
        this.securityService = securityService;
        this.userService = userService;
    }
    
    /**
     * Authenticates a user and generates a JWT token.
     * *
     * @param auth DTO with authentication credentials.
     * @return ResponseEntity with JWT token and user data.
     */
    @Operation(
        summary = "Authenticate to the application",
        description = "Verify credentials and generate an access token."
    )
    @SwaggerApiResponses
    @ApiResponse(
        responseCode = "200",
        description = "Authentication successful",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = TokenResponse.class),
            examples = {@ExampleObject(
                value = SwaggerResponseExample.EXAMPLE_GET_RESOURCE_WITH_TOKEN
            )}
        )
    )
    @PostMapping("login")
    public ResponseEntity<TokenResponse> authenticate(@Valid @RequestBody AuthTO auth) {
        TokenResponse tokenResponse = securityService.authenticate(auth);
        return ResponseEntity.ok(tokenResponse);
    }
    
    /**
     * Register a new user in the system.
     * @param user DTO with data of the user to be registered.
     * @return Standardized response with the created user.
     */
    @Operation(
        summary = "Register user",
        description = "Register a user in the system with an encrypted password and their role."
    )
    @SwaggerApiResponses
    @ApiResponse(
        responseCode = "201",
        description = "User created successfully.",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = APIResponseData.class),
            examples = @ExampleObject(
                value = SwaggerResponseExample.CREATED_EXAMPLE
            )
        )
    )
    @PostMapping("register")
    public ResponseEntity<APIResponseData> register(@Valid @RequestBody UserTO user) {
        UserTO registeredUser = userService.createUser(user);
        return APIResponseHandler.handleResponse(APISuccess.RESOURCE_CREATED, registeredUser);
    }
}
