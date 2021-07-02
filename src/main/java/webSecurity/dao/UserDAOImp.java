package webSecurity.dao;

import webSecurity.models.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import java.util.List;


@Repository
public class UserDAOImp implements UserDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> listAllPeople() {
        return entityManager.createQuery("from User", User.class
        ).getResultList();
    }

    @Override
    public User getUserById(Long id) {
        TypedQuery<User> q = entityManager.createQuery(
                "select user from User user where user.id = :id", User.class
        );
        q.setParameter("id", id);
        return q.getResultList().stream().findAny().orElse(null);
    }

    @Override
    public void save(User user) {
        entityManager.persist(user);

    }

    @Override
    public void update(Long id, User updatedUser) {
        entityManager.merge(updatedUser);
    }

    @Override
    public void delete(Long id) {
        entityManager.remove(getUserById(id));
    }

    @Override
    public User getUserByName(String name) {
        return entityManager.createQuery("SELECT u FROM User u WHERE u.name = :userName", User.class)
                .setParameter("userName", name)
                .setMaxResults(1)
                .getSingleResult();
    }
}

