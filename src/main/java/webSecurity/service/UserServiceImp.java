package webSecurity.service;

import webSecurity.dao.UserDAO;
import webSecurity.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

@Service
@Transactional()
public class UserServiceImp implements UserService {

    @Qualifier("userDAOImp")
    @Autowired
    private UserDAO userDao;

    @Override
    public List<User> getAllUsers() {
        return userDao.listAllPeople();
    }

    @Override
    public User getUserById(Long id) {
        return userDao.getUserById(id);
    }

    @Override
    public void addUser(User user) {
        userDao.save(user);
    }

    @Override
    public void updateUser(Long id, User updatedUser) {
        userDao.update(id, updatedUser);
    }

    @Override
    public void deleteUser(Long id) {
        userDao.delete(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.getUserByName(username);
        return user;
    }

    @Override
    public User loadUserByUsername(Principal principal) throws UsernameNotFoundException {
        User user = userDao.getUserByName(principal.getName());
        return user;
    }
}
