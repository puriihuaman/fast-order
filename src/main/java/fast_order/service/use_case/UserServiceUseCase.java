package fast_order.service.use_case;

import fast_order.dto.UserTO;

import java.util.List;
import java.util.UUID;

public interface UserServiceUseCase {
    List<UserTO> findAllUsers();
    
    UserTO findUserById(UUID id);
    
    UserTO findUserByEmail(String email);
    
    UserTO createUser(UserTO user);
    
    UserTO updateUser(UUID id, UserTO user);
    
    void deleteUser(UUID id);
}
