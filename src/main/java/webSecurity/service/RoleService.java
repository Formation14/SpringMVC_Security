package webSecurity.service;

import webSecurity.model.Role;

import java.util.List;
import java.util.Set;

public interface RoleService {
    List<Role> getAllRoles();

    Set<Role> getSetRoles(Set<String> roles);

    Role getUserRole();

    Role getAdminRole();

    void setUserRole();

    void setAdminRole();

    void setRolesDefault();

    List<Role> getAllDefRoles();
}
