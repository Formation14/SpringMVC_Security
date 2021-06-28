package webSecurity.dao;

import webSecurity.model.User;

public interface UserDao {
    User getUserByName(String name);
}
