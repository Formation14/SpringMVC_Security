package webSecurity.dao;

import webSecurity.model.Role;
import webSecurity.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.*;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public User getUserByName(String name) {
        return entityManager.find(User.class, name);

    }

    @Override
    public Role showRole(int id) {
        TypedQuery<Role> typedQuery = entityManager.createQuery("SELECT r FROM Role r where r.id = :id", Role.class);
        return typedQuery.getSingleResult();
    }

    @Override
    public void update(User user, String[] role) {
        Set<Role> rol = new HashSet<>();
        for (String s : role) {
            if (s.equals("ADMIN")) {
                rol.add(showRole(1));
            } else {
                rol.add(showRole(2));
            }
        }
        user.setRoles(rol);
        entityManager.merge(user);

    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Override
    public User getUser(int id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void addUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public void deleteUser(User user) {
        entityManager.remove(user);
    }

}
