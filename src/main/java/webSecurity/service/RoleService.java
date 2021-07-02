package webSecurity.service;

import webSecurity.models.Role;

import java.util.List;
import java.util.Set;

public interface RoleService {

    List<Role> getAllRoles();

    Set<Role> getRoleSet(Set<String> roles);

    Role getDefaultRole();

    Role getAdminRole();

    void setRolesDefault();
}
