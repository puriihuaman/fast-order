package fast_order.controller;

import fast_order.dto.AuthTO;
import fast_order.dto.UserTO;
import fast_order.enums.APISuccess;
import fast_order.dto.ErrorResponseTO;
import fast_order.security.SecurityService;
import fast_order.security.TokenResponse;
import fast_order.service.UserService;
import fast_order.utils.APIResponseData;
import fast_order.utils.APIResponseHandler;
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

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@Tag(name = "Auth", description = "Endpoint para gestionar la autenticación")
public class AuthController {
    private final SecurityService securityService;
    private final UserService userService;
    
    public AuthController(final SecurityService securityService, UserService userService) {
        this.securityService = securityService;
        this.userService = userService;
    }
    
    @Operation(
        summary = "Autenticarse en la aplicación",
        description = "Verificar credenciales y generar un token de acceso.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos de usuario para iniciar sesión.", required = true,
            content = @Content(
                schema = @Schema(implementation = AuthTO.class), examples = @ExampleObject(
                value = """
                        {
                          "email": "bytes@colaborativos.es",
                          "password": "AdM¡N&20_25"
                        }
                        """
            )
            )
        ), responses = {
        @ApiResponse(
            responseCode = "200", description = "Autenticación exitosa", content = @Content(
            schema = @Schema(implementation = TokenResponse.class), examples = @ExampleObject(
            value = """
                    {
                      "access_token": "eyJhbGciOiJIUzI1NiJ9.xxx.xxx.xxx.xxx.xxx",
                      "email": "admin@admin.com",
                      "role": "admin"
                    }
                    """
        )
        )
        ), @ApiResponse(
        responseCode = "400", description = "Error de validación en el cuerpo de la solicitud.",
        content = @Content(
            schema = @Schema(implementation = ErrorResponseTO.class), examples = @ExampleObject(
            value = """
                    {
                      "hasError": true,
                      "title": "Datos inválidos",
                      "message": "Los datos solicitados contienen valores no válidos o un formato incorrecto.",
                      "code": 400,
                      "reasons": {
                        "email": "El campo email está vacío."
                      },
                      "timestamp": "2025-03-07T22:08:21.178014"
                    }
                    """
        )
        )
    ), @ApiResponse(
        responseCode = "401", description = "Credenciales invalidas", content = @Content(
        schema = @Schema(implementation = ErrorResponseTO.class), examples = @ExampleObject(
        value = """
                {
                  "hasError": true,
                  "title": "Credenciales invalidas",
                  "message": "Email o contraseña incorrecto. Inténtalo de nuevo con las credenciales correctas.",
                  "code": 401,
                  "reasons": null,
                  "timestamp": "2025-03-10T22:58:53.8336704"
                }
                """
    )
    )
    )
    }
    )
    @PostMapping("login")
    public ResponseEntity<TokenResponse> authenticate(@Valid @RequestBody AuthTO auth) {
        TokenResponse tokenResponse = securityService.authenticate(auth);
        return ResponseEntity.ok(tokenResponse);
    }
    
    @PostMapping("register")
    public ResponseEntity<APIResponseData> register(@Valid @RequestBody UserTO user) {
        UserTO registeredUser = userService.createUser(user);
        return APIResponseHandler.handleResponse(APISuccess.RESOURCE_CREATED, registeredUser);
    }
}
