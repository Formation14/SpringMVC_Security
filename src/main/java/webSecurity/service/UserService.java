package webSecurity.service;

import webSecurity.model.User;

import java.util.List;

public interface UserService{
    List<User> getAllUsers();
    User getUser(int id);
    void addUser(User user);
    void deleteUser(User user);
    void update(User user);
}
