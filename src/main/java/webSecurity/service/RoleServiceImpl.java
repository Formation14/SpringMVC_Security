package webSecurity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webSecurity.dao.RoleDao;
import webSecurity.model.Role;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {

    private static final String ADMINROLE = "ADMIN";
    private static final String USSERROLE = "USER";
    @Autowired
    private RoleDao roleDao;

    @Override
    @Transactional(readOnly = true)
    public List<Role> getAllRoles() {
        return roleDao.getAllRoles();
    }

    @Override
    @Transactional
    public Set<Role> getSetRoles(Set<String> roles) {
        return roleDao.getRoleSet(roles);
    }

    @Override
    @Transactional
    public Role getUserRole() {
        return roleDao.getUserRole();
    }

    @Override
    @Transactional
    public Role getAdminRole() {
        return roleDao.getAdminRole();
    }

    @Override
    @Transactional
    public void setUserRole() {
        roleDao.setUserRoleDefault();
    }

    @Override
    @Transactional
    public void setAdminRole() {
        roleDao.setAdminRoleDefault();
    }

    @Override
    public List<Role> getAllDefRoles() {
        List<Role> list = new ArrayList<>();
        list.add(new Role("ADMIN"));
        list.add(new Role("USER"));
        return list;
    }

}
