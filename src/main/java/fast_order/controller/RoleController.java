package fast_order.controller;

import fast_order.dto.RoleTO;
import fast_order.enums.APISuccess;
import fast_order.enums.RoleType;
import fast_order.service.RoleService;
import fast_order.utils.APIResponseData;
import fast_order.utils.APIResponseHandler;
import fast_order.annotation.SwaggerApiResponses;
import fast_order.utils.SwaggerResponseExample;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for role management.
 * *
 * Exposes endpoints for:
 * - Gets a list of all roles.
 * - Get a role by an ID.
 * - Obtains a role through its name.
 * - Register new role in the system.
 * *
 * All responses follow the standard format defined in {@link APIResponseData}.
 * *
 * @see RoleService Role management service.
 */
@RestController
@RequestMapping(value = "/roles", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@Tag(name = "Role", description = "Endpoints responsible for managing roles.")
public class RoleController {
    private final RoleService roleService;
    
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }
    
    /**
     * Gets all roles registered in the system.
     * @return ResponseEntity with a list of roles in a standardized format.
     * *
     * @see RoleService#findRoles()
     */
    @Operation(summary = "List of roles", description = "Gets a list of all roles.")
    @SwaggerApiResponses
    @ApiResponse(
        responseCode = "200",
        description = "Roles successfully obtained.",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = APIResponseData.class),
            examples = @ExampleObject(
                value = SwaggerResponseExample.EXAMPLE_GET_ALL_RESOURCE
            )
        )
    )
    @GetMapping("all")
    public ResponseEntity<APIResponseData> findAllRoles() {
        List<RoleTO> roles = roleService.findRoles();
        return APIResponseHandler.handleResponse(APISuccess.RESOURCE_RETRIEVED, roles);
    }
    
    /**
     * Search for a specific role by its unique ID.
     * @param id Unique role identifier (required).
     * @return ResponseEntity with data from the role found.
     * *
     * @see RoleTO Role data structure.
     * @see RoleService#findRoleById(Long)
     */
    @Operation(
        summary = "Gets a role",
        description = "Gets a role through its ID.",
        parameters = @Parameter(
            name = "id",
            description = "ID of the role to search for.",
            example = "2",
            required = true,
            in = ParameterIn.PATH
        )
    )
    @SwaggerApiResponses
    @ApiResponse(
        responseCode = "200",
        description = "Role successfully obtained.",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = APIResponseData.class),
            examples = @ExampleObject(
                value = SwaggerResponseExample.EXAMPLE_GET_RESOURCE
            )
        )
    )
    @GetMapping("id/{id}")
    public ResponseEntity<APIResponseData> findRoleById(@PathVariable("id") Long id) {
        RoleTO role = roleService.findRoleById(id);
        return APIResponseHandler.handleResponse(APISuccess.RESOURCE_RETRIEVED, role);
    }
    
    /**
     * Search for a specific role by its unique name.
     * @param name Role name (required).
     * @return ResponseEntity with data from the role found.
     * *
     * @see RoleTO Role data structure.
     * @see RoleService#findRoleByRoleName(RoleType)
     */
    @Operation(
        summary = "Gets a role",
        description = "Gets a role through its name.",
        parameters = @Parameter(
            name = "name",
            description = "Name of the role to search for.",
            example = "admin",
            required = true,
            in = ParameterIn.PATH
        )
    )
    @SwaggerApiResponses
    @ApiResponse(
        responseCode = "200",
        description = "Role successfully obtained.",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = APIResponseData.class),
            examples = @ExampleObject(
                value = SwaggerResponseExample.EXAMPLE_GET_RESOURCE
            )
        )
    )
    @GetMapping("role/{name}")
    public ResponseEntity<APIResponseData> findRoleByRoleName(@PathVariable("name") RoleType name) {
        RoleTO role = roleService.findRoleByRoleName(name);
        return APIResponseHandler.handleResponse(APISuccess.RESOURCE_RETRIEVED, role);
    }
    
    /**
     * Create a new role in the system.
     * @param role DTO with new role data (automatically validated).
     * @return ResponseEntity with the created role.
     * *
     * @see RoleTO Role data structure.
     * @see RoleService#createRole(RoleTO) 
     */
    @Operation(summary = "Create a role", description = "Create a role with all the required data.")
    @SwaggerApiResponses
    @ApiResponse(
        responseCode = "201",
        description = "Role successfully registered.",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = APIResponseData.class),
            examples = @ExampleObject(
                value = SwaggerResponseExample.CREATED_EXAMPLE
            )
        )
    )
    @PostMapping("create")
    public ResponseEntity<APIResponseData> createRole(@Valid @RequestBody RoleTO role) {
        RoleTO savedRole = roleService.createRole(role);
        return APIResponseHandler.handleResponse(APISuccess.RESOURCE_CREATED, savedRole);
    }
}




























