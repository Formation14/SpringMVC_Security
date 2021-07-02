package webSecurity.dao;

import org.springframework.stereotype.Repository;
import webSecurity.model.Role;
import webSecurity.model.User;

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
        return entityManager.createQuery("select u from User u where u.name = :user_name", User.class)
                .setParameter("user_name", name)
                .setMaxResults(1)
                .getSingleResult();

    }

    @Override
    public Role showRole(int id) {
        TypedQuery<Role> typedQuery = entityManager.createQuery("select r from Role r where r.id = :id", Role.class);
        return typedQuery.getSingleResult();
    }

    @Override
    public void update(Integer id, User user) {
        entityManager.merge(user);

    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    public User getUser(int id) {
        TypedQuery<User> q = entityManager.createQuery(
                "select u from User u where u.id = :id", User.class);
        q.setParameter("id", id);
        return q.getResultList().stream().findAny().orElse(null);
    }

    @Override
    public void addUser(User user) {
        entityManager.persist(user);
    }


    @Override
    public void deleteUser(Integer id) {
        entityManager.remove(getUser(id));
    }

}
