package webSecurity.dao;

import webSecurity.model.Role;
import webSecurity.model.User;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class UserDaoImpl implements UserDao {
    private  List <User> userList = Stream.of(new User(1L, "test", "test", Collections.singleton(new Role(1L, "ROLE_USER")))).collect(Collectors.toList());

    @Override
    public User getUserByName(String name) {
        return userList.stream().filter(user -> user.getName().equals(name)).findFirst().orElse(null);
    }

    @Override
    public List<User> getAllUsers() {
        return userList;
    }

    @Override
    public User getUser(int id) {
        return null;
    }

    @Override
    public void addUser(User user) {

    }

    @Override
    public void deleteUser(User user) {

    }

    @Override
    public void update(User user) {

    }
}

