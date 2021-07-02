package webSecurity.dao;

import webSecurity.models.User;

import java.util.List;

public interface UserDAO {
    List<User> listAllPeople();

    User getUserById(Long id);

    void save(User user);

    void update(Long id, User updatedUser);

    void delete(Long id);

    User getUserByName(String name);
}

