package webSecurity.dao;

import webSecurity.model.Role;
import webSecurity.model.User;

import java.util.List;

public interface UserDao {
    User getUserByName(String name);
    Role showRole(int id);
    void update(Integer id, User user);
    List<User> getAllUsers();
    User getUser(int id);
    void addUser(User user);
    void deleteUser(User user);
}
