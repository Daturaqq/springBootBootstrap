package com.springBoot.dao;

import com.springBoot.model.Role;
import com.springBoot.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<User> getAllUsers() {
        return (List<User>) entityManager.createQuery("select u from User u").getResultList();
    }

    @Override
    public User getUser(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void add(User user) {
        entityManager.persist(user);
    }

    @Override
    public void edit(User user) {
        entityManager.merge(user);
    }

    @Override
    public void delete(Long id) {
        entityManager.remove(entityManager.find(User.class, id));
    }

    @Override
    public User getUserByUsername(String email) {
        TypedQuery<User> query = entityManager.createQuery("SELECT u from User u WHERE u.email = :email", User.class);
        query.setParameter("email", email);
        return query.getSingleResult();
    }

    @Override
    public Role getRole(String roleName) {
        TypedQuery<Role> query = entityManager.createQuery("SELECT r from Role r WHERE r.role = :roleName", Role.class);
        query.setParameter("roleName", roleName);
        return query.getSingleResult();
    }

    @Override
    public void createAdmin() {
        if (getAllUsers().isEmpty()) {
            add(new User(
                    "ADMIN",
                    "ADMIN",
                    22,
                    "ADMIN@ADMIN.com",
                    "ADMIN",
                    new HashSet<Role>() {{
                        add(new Role("ROLE_ADMIN"));
                    }})
            );
            add(new User(
                    "Vasya",
                    "Petrov",
                    31,
                    "Petrov@mail.ru",
                    "1234",
                    new HashSet<Role>() {{
                        add(new Role("ROLE_USER"));
                    }})
            );
        }
    }
}
