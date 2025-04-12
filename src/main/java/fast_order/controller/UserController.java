package fast_order.controller;

import fast_order.dto.UserTO;
import fast_order.enums.APISuccess;
import fast_order.service.UserService;
import fast_order.utils.APIResponseData;
import fast_order.utils.APIResponseHandler;
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

@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class UserController {
    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping("all")
    public ResponseEntity<APIResponseData> findAllUsers() {
        List<UserTO> users = userService.findAllUsers();
        return APIResponseHandler.handleResponse(APISuccess.RESOURCE_RETRIEVED, users);
        
    }
    
    @GetMapping("id/{id}")
    public ResponseEntity<APIResponseData> findUserById(@PathVariable("id") Long id) {
        UserTO user = userService.findUserById(id);
        return APIResponseHandler.handleResponse(APISuccess.RESOURCE_RETRIEVED, user);
    }
    
    @GetMapping("email/{email}")
    public ResponseEntity<APIResponseData> findUserByEmail(@PathVariable("email") String email) {
        UserTO user = userService.findUserByEmail(email);
        return APIResponseHandler.handleResponse(APISuccess.RESOURCE_RETRIEVED, user);
    }
    
    @PostMapping("create")
    public ResponseEntity<APIResponseData> createUser(@Valid @RequestBody UserTO user) {
        UserTO createdUser = userService.createUser(user);
        return APIResponseHandler.handleResponse(APISuccess.RESOURCE_CREATED, createdUser);
    }
    
    @PutMapping("update/{id}")
    public ResponseEntity<APIResponseData> updateUser(
        @PathVariable("id") Long id,
        @Valid @RequestBody UserTO user
    )
    {
        UserTO updatedUser = userService.updateUser(id, user);
        return APIResponseHandler.handleResponse(APISuccess.RESOURCE_CREATED, updatedUser);
    }
    
    @DeleteMapping("delete/{id}")
    public ResponseEntity<APIResponseData> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return APIResponseHandler.handleResponse(APISuccess.RESOURCE_REMOVED, null);
    }
}
