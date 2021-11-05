package com.springBoot.dao;

import com.springBoot.model.Role;
import com.springBoot.model.User;

import java.util.List;

public interface UserDao {
    List<User> getAllUsers();

    User getUser(Long id);

    void add(User user);

    void edit(User user);

    void delete(Long id);

    User getUserByUsername(String email);

    void createAdmin();

    Role getRole(String roleName);
}
