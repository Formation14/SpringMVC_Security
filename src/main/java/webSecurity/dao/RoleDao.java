package webSecurity.dao;

import webSecurity.model.Role;

import java.util.List;
import java.util.Set;

public interface RoleDao {
    Set<Role> getRoleSet(Set<String> role);

    Role getUserRole();

    Role getRoleByName(String name);

    Role getAdminRole();

    void setAdminRoleDefault();

    void setUserRoleDefault();

    List<Role> getAllRoles();
}
