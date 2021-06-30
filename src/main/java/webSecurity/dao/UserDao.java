package webSecurity.dao;

import webSecurity.model.Role;
import webSecurity.model.User;

import java.util.List;

public interface UserDao {
    User getUserByName(String name);
    Role showRole(int id);
    void update(User user, String[] role);
    List<User> getAllUsers();
    User getUser(int id);
    void addUser(User user);
    void deleteUser(User user);
}
