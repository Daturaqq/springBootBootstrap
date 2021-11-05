package com.springBoot.service;

import com.springBoot.model.Role;
import com.springBoot.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User getUser(Long id);

    void add(User user, String[] role);

    void edit(User editUser, long id, String[] roles);

    void delete(Long id);

    Role getRole(String roleName);

    User getUserByUsername(String username);

    void createAdmin();

    List<Role> getRoleList();
}
