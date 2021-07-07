package webSecurity.service;

import webSecurity.dao.RoleDao;
import webSecurity.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleDao roleDao;

    @Autowired
    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public List<Role> getAllRoles() {
        return roleDao.listAllRoles();
    }

    @Override
    public Set<Role> getRoleSet(Set<String> roles) {
        return roleDao.getRoleSet(roles);
    }

    @Override
    public Role getDefaultRole() {
        return roleDao.getDefaultRole();
    }

    @Override
    public Role getAdminRole() {
        return roleDao.getAdminRole();
    }

    @Override
    public void setRolesDefault() {
        roleDao.setAdminRoleDefault();
        roleDao.setUserRoleDefault();
    }
}
