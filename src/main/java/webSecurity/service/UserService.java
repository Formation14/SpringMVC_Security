package webSecurity.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import webSecurity.model.User;

import java.security.Principal;
import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> getAllUsers();
    User getUser(int id);
    void addUser(User user);
    void deleteUser(Integer id);
    void updateUser(Integer id, User user);
    User loadUserByUsername(Principal principal);
}
