package webSecurity.service;

import webSecurity.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.security.Principal;
import java.util.List;

public interface UserService extends UserDetailsService {
    void addUser(User user);

    List<User> getAllUsers();

    void deleteUser(Long id);

    void updateUser(Long id, User updatedUser);

    User getUserById(Long id);

    User getUserByName(Principal principal);

    void creatDefaultUser();

    User chooseRole(User user, String[] chooseRole);
}
