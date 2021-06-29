package webSecurity.dao;

import webSecurity.model.User;

import java.util.List;

public interface UserDao {
    User getUserByName(String name);
    List<User> getAllUsers();
    User getUser(int id);
    void addUser(User user);
    void deleteUser(User user);
    void update(User user);
}
