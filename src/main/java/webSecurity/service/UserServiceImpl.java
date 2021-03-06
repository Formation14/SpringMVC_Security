package webSecurity.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
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

@Service("userDetailsServiceImpl")
@Transactional
public class UserServiceImpl implements UserService , UserDetailsService{

    private final UserDAO userDao;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public UserServiceImpl(RoleService roleService, PasswordEncoder passwordEncoder, UserDAO userDao) {
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.userDao = userDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userDao.listAllPeople();
    }

    @Override
    public User getUserById(Long id) {
        return userDao.getUserById(id);
    }

    @Override
    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.save(user);
    }

    @Override
    public void updateUser(Long id, User updatedUser) {
        updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        userDao.update(id, updatedUser);
    }

    @Override
    public void deleteUser(Long id) {
        userDao.delete(id);
    }



    @Override
    @Transactional(readOnly = true)
    public User getUserByName(Principal principal) throws UsernameNotFoundException {
        return userDao.getUserByName(principal.getName());
    }

    public User chooseRole(User user, String[] chooseRole) {
        for (String role : chooseRole) {
            if (role.contains("ROLE_USER")) {
                user.getRoleSet().add(roleService.getDefaultRole());
            } else if (role.contains("ROLE_ADMIN")) {
                user.getRoleSet().add(roleService.getAdminRole());
            }
        }
        return user;
    }

    @Override
    public void creatDefaultUser() {
        roleService.setRolesDefault();
        User admin = new User();
        admin.setAge(26);
        admin.setEmail("paveltis@tut.by");
        admin.setName("admin");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.getRoleSet().add(roleService.getAdminRole());
        userDao.save(admin);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userDao.getUserByName(s);
        if (user == null) {
            throw new UsernameNotFoundException("No user found with username: "+ s);
        }
        return User.fromUser(user);
    }
}
