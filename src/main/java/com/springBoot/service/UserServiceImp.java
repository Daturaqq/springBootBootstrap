package com.springBoot.service;

import com.springBoot.dao.UserDao;
import com.springBoot.model.Role;
import com.springBoot.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserServiceImp implements UserService, UserDetailsService {
    private final UserDao DAO;

    public UserServiceImp(UserDao DAO) {
        this.DAO = DAO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return DAO.getAllUsers();
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(Long id) {
        return DAO.getUser(id);
    }

    @Override
    @Transactional
    public void add(User user, String[] roles) {
        user.setRoles(getRoleFromString(roles));
        DAO.add(user);
    }

    @Override
    @Transactional
    public void edit(User editUser, long id, String[] roles) {
        User user = DAO.getUser(id);
        user.setFirstname(editUser.getFirstname());
        user.setLastname(editUser.getLastname());
        user.setAge(editUser.getAge());
        user.setEmail(editUser.getEmail());
        user.setPassword(editUser.getPassword());
        user.setRoles(getRoleFromString(roles));
        DAO.edit(user);
    }

    private Set<Role> getRoleFromString(String[] roles) {
        Set<Role> roleSet = new HashSet<Role>();
        Role admin = new Role("ROLE_ADMIN");
        Role user = new Role(("ROLE_USER"));
        for (String role : roles) {
            if (role.equals("ADMIN") && !roleSet.contains(admin)) {
                roleSet.add(admin);
            } else if (role.equals("USER") && !roleSet.contains(user)) {
                roleSet.add(new Role("ROLE_USER"));
            }
        }
        return roleSet;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        DAO.delete(id);
    }

    @Override
    @Transactional
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        return DAO.getUserByUsername(email);
    }

    @Override
    @Transactional
    public Role getRole(String roleName) {
        return DAO.getRole(roleName);
    }

    @Override
    @Transactional
    public User getUserByUsername(String username) {
        return loadUserByUsername(username);
    }

    @Override
    @Transactional
    public void createAdmin() {
        DAO.createAdmin();
    }

    @Override
    @Transactional
    public List<Role> getRoleList() {
        List<Role> roleList = new ArrayList<Role>() {
            {
                add(new Role("ROLE_USER"));
            }

            {
                add(new Role("ROLE_ADMIN"));
            }
        };
        return roleList;
    }
}
