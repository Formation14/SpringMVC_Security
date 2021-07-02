package webSecurity.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import webSecurity.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class RoleDaoImpl implements RoleDao {

    static String USSERROLE = "USER";
    static String ADMINROLE = "ADMIN";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Set<Role> getRoleSet(Set<String> role) {
        return new HashSet<>(entityManager.createQuery("select r from Role r where r.role in (:rolesSet)")
                .setParameter("rolesSet", role)
                .getResultList());
    }

    @Override
    public Role getUserRole() {
        return getRoleByName(USSERROLE);
    }

    @Override
    @Transactional
    public Role getRoleByName(String name) {
        return entityManager.createQuery("select r from Role r where r.role = :role", Role.class)
                .setParameter("role", name)
                .setMaxResults(1)
                .getSingleResult();
    }

    @Override
    public Role getAdminRole() {
        return getRoleByName(ADMINROLE);
    }

    @Override
    public void setAdminRoleDefault() {

        Role adminRole = new Role();
        adminRole.setRole("ADMIN");
        entityManager.persist(adminRole);
    }

    @Override
    public void setUserRoleDefault() {
        Role userRole = new Role();
        userRole.setRole("USER");
        entityManager.persist(userRole);
    }

    @Override
    public List<Role> getAllRoles() {
        return  entityManager.createQuery("select r from Role r", Role.class).getResultList();
    }
}
