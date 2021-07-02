package webSecurity.dao;

import webSecurity.models.Role;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class RoleDaoImpl implements RoleDao {

    final private String defaultRoleName = "ROLE_USER";
    final private String adminRoleName = "ROLE_ADMIN";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Set<Role> getRoleSet(Set<String> role) {
        return new HashSet<>(entityManager.createQuery("SELECT r FROM Role r WHERE r.name in (:roleStringSet)")
                .setParameter("roleStringSet", role)
                .getResultList());
    }

    @Override
    public Role getDefaultRole() {
        return getRoleByName(defaultRoleName);
    }

    @Override
    @Transactional
    public Role getRoleByName(String name) {
        return entityManager.createQuery("SELECT r FROM Role r WHERE r.name = :roleName", Role.class)
                .setParameter("roleName", name)
                .setMaxResults(1)
                .getSingleResult();
    }

    @Override
    public Role getAdminRole() {
        return getRoleByName(adminRoleName);
    }

    @Override
    public void setAdminRoleDefault() {

        Role adminRole = new Role();
        adminRole.setName("ROLE_ADMIN");
        entityManager.persist(adminRole);
    }

    @Override
    public void setUserRoleDefault() {
        Role userRole = new Role();
        userRole.setName("ROLE_USER");
        entityManager.persist(userRole);
    }

    @Override
    public List<Role> listAllRoles() {
        return  entityManager.createQuery("from Role", Role.class
        ).getResultList();
    }
}
