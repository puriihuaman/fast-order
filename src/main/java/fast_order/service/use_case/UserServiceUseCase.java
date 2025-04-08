package fast_order.service.use_case;

import fast_order.dto.UserTO;

import java.util.List;

public interface UserServiceUseCase {
    List<UserTO> findAllUsers();
    
    UserTO findUserById(Long id);
    
    UserTO findUserByEmail(String email);
    
    UserTO createUser(UserTO user);
    
    UserTO updateUser(Long id, UserTO user);
    
    void deleteUser(Long id);
}
