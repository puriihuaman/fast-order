package fast_order.controller;

import fast_order.dto.UserTO;
import fast_order.commons.enums.APISuccess;
import fast_order.service.UserService;
import fast_order.utils.APIResponseData;
import fast_order.utils.APIResponseHandler;
import fast_order.commons.annotation.SwaggerApiResponses;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for user management.
 * *
 * Exposes endpoints for:
 * - Gets a list of all users.
 * - Get a user by an ID.
 * - Get a user through their email.
 * - Register new user in the system.
 * - Updates an existing user in the system.
 * - Delete a user in the system.
 * *
 * All responses follow the standard format defined in {@link APIResponseData}.
 * *
 * @see UserService User management service.
 */
@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@Tag(name = "User", description = "Endpoints responsible for managing users.")
public class UserController {
    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    /**
     * Gets all users registered in the system.
     * @return ResponseEntity with a list of users in a standardized format.
     * *
     * @see UserService#findAllUsers()
     */
    @Operation(summary = "User list", description = "Gets a list of all users.")
    @SwaggerApiResponses
    @ApiResponse(
        responseCode = "200",
        description = "Users successfully obtained.",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = APIResponseData.class),
            examples = @ExampleObject(
                value = SwaggerResponseExample.EXAMPLE_GET_ALL_RESOURCE
            )
        )
    )
    @GetMapping("all")
    public ResponseEntity<APIResponseData> findAllUsers() {
        List<UserTO> users = userService.findAllUsers();
        return APIResponseHandler.handleResponse(APISuccess.RESOURCE_RETRIEVED, users);
    }
    
    /**
     * Search for specific user by its unique ID.
     * @param id Unique user identifier (required)
     * @return ResponseEntity with data from the user found.
     * *
     * @see UserTO User data structure.
     * @see UserService#findUserById(Long)
     */
    @Operation(
        summary = "Get user",
        description = "Gets a user through its ID.",
        parameters = {
            @Parameter(
                name = "id",
                description = "ID of the user to search for.",
                example = "12",
                required = true,
                in = ParameterIn.PATH
            )
        }
    )
    @SwaggerApiResponses
    @ApiResponse(
        responseCode = "200",
        description = "User successfully obtained.",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = APIResponseData.class),
            examples = @ExampleObject(
                value = SwaggerResponseExample.EXAMPLE_GET_RESOURCE
            )
        )
    )
    @GetMapping("id/{id}")
    public ResponseEntity<APIResponseData> findUserById(@PathVariable("id") Long id) {
        UserTO user = userService.findUserById(id);
        return APIResponseHandler.handleResponse(APISuccess.RESOURCE_RETRIEVED, user);
    }
    
    /**
     * Search for a specific user by its unique email.
     * @param email User email (required).
     * @return ResponseEntity with data from the user found.
     * *
     * @see UserTO User data structure.
     * @see UserService#findUserByEmail(String)
     */
    @Operation(
        summary = "Get user",
        description = "Get a user through their email.",
        parameters = {
            @Parameter(
                name = "email",
                description = "Email of the user to search for.",
                example = "jorge@gmail.com",
                required = true,
                in = ParameterIn.PATH
            )
        }
    )
    @SwaggerApiResponses
    @ApiResponse(
        responseCode = "200",
        description = "User successfully obtained.",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = APIResponseData.class),
            examples = @ExampleObject(
                value = SwaggerResponseExample.EXAMPLE_GET_RESOURCE
            )
        )
    )
    @GetMapping("email/{email}")
    public ResponseEntity<APIResponseData> findUserByEmail(@PathVariable("email") String email) {
        UserTO user = userService.findUserByEmail(email);
        return APIResponseHandler.handleResponse(APISuccess.RESOURCE_RETRIEVED, user);
    }
    
    /**
     * Create a new user in the system.
     * @param user DTO with new user data (automatically validated).
     * @return ResponseEntity with the created user.
     * *
     * @see UserTO User data structure.
     * @see UserService#createUser(UserTO)
     */
    @Operation(summary = "Create user", description = "Create a user with al the required data.")
    @SwaggerApiResponses
    @ApiResponse(
        responseCode = "201",
        description = "Successfully registered user.",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = APIResponseData.class),
            examples = @ExampleObject(
                value = SwaggerResponseExample.CREATED_EXAMPLE
            )
        )
    )
    @PostMapping("create")
    public ResponseEntity<APIResponseData> createUser(@Valid @RequestBody UserTO user) {
        UserTO createdUser = userService.createUser(user);
        return APIResponseHandler.handleResponse(APISuccess.RESOURCE_CREATED, createdUser);
    }
    
    /**
     * Update an existing user.
     * @param id Unique identifier of the user to be updated.
     * @param user Updated user data.
     * @return ResponseEntity with the updated user.
     * *
     * @see UserTO User data structure.
     * @see UserService#updateUser(Long, UserTO)
     */
    @Operation(
        summary = "Update user",
        description = "Update a specific user.",
        parameters = {
            @Parameter(
                name = "id",
                description = "User ID to search for update.",
                example = "12",
                required = true,
                in = ParameterIn.PATH
            )
        }
    )
    @SwaggerApiResponses
    @ApiResponse(
        responseCode = "200",
        description = "User successfully updated.",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = APIResponseData.class),
            examples = @ExampleObject(
                value = SwaggerResponseExample.CREATED_EXAMPLE
            )
        )
    )
    @PutMapping("update/{id}")
    public ResponseEntity<APIResponseData> updateUser(
        @PathVariable("id") Long id,
        @Valid @RequestBody UserTO user
    )
    {
        UserTO updatedUser = userService.updateUser(id, user);
        return APIResponseHandler.handleResponse(APISuccess.RESOURCE_CREATED, updatedUser);
    }
    
    /**
     * Delete an existing user.
     * @param id Unique identifier of the user to be updated.
     * @return Empty ResponseEntity with code 204.
     * *
     * @see UserService#deleteUser(Long)
     */
    @Operation(
        summary = "Delete user",
        description = "Delete a user using their ID.",
        parameters = {
            @Parameter(
                name = "id",
                description = "ID of the user to be deleted.",
                example = "12",
                required = true,
                in = ParameterIn.PATH
            )
        }
    )
    @SwaggerApiResponses
    @ApiResponse(
        responseCode = "204",
        description = "Successfully deleted user.",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = APIResponseData.class),
            examples = @ExampleObject(
                value = SwaggerResponseExample.EXAMPLE_DELETE_RESOURCE
            )
        )
    )
    @DeleteMapping("delete/{id}")
    public ResponseEntity<APIResponseData> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return APIResponseHandler.handleResponse(APISuccess.RESOURCE_REMOVED, null);
    }
}
