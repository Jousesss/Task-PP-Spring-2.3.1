package ru.alkey.spring.mvc.dao.impl;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import ru.alkey.spring.mvc.dao.UserDAO;
import ru.alkey.spring.mvc.model.User;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    private static final String FIND_ALL_QUERY = "FROM User";
    private static final String FIND_BY_EMAIL_QUERY = "FROM User u WHERE u.email = :email";
    private static final String REMOVE_BY_ID_QUERY = "DELETE FROM User u WHERE u.id = :id";

    @Override
    public List<User> findAllUsers() {
        List<User> usersFromBD;

        try {
            usersFromBD = entityManager.createQuery(FIND_ALL_QUERY, User.class).getResultList();
        } catch (DataAccessException e) {
            throw new RuntimeException("Something went wrong in method findAll(), UserDAOImpl.class", e);
        }

        return usersFromBD;
    }

    @Override
    public Optional<User> findUser(long id) {
        User userFromBD;

        try {
            userFromBD = entityManager.find(User.class, id);
        } catch (DataAccessException e) {
            throw new RuntimeException("Something went wrong in method findUser(long id)", e);
        }

        return Optional.ofNullable(userFromBD);
    }

    @Override
    public Optional<User> findUser(String email) {
        User userFromBD = null;

        try {
            TypedQuery<User> queryJPQL = entityManager.createQuery(FIND_BY_EMAIL_QUERY, User.class);
            queryJPQL.setParameter("email", email);
            userFromBD = queryJPQL.getSingleResult();
        } catch (DataAccessException e) {
            throw new RuntimeException("Something went wrong in method findUser(long id)", e);
        }

        return Optional.ofNullable(userFromBD);
    }

    @Override
    public User saveUser(User user) {
        try {
            entityManager.persist(user);
        } catch (DataAccessException e) {
            throw new RuntimeException("Something went wrong in method saveUser(User user)", e);
        }

        return user;
    }

    @Override
    public void updateUser(User user) {
        try {
            entityManager.merge(user);
        } catch (DataAccessException e) {
            throw new RuntimeException("Something went wrong in method updateUser(User user)", e);
        }
    }

    @Override
    public void removeUser(long id) {
        try {
            Query queryJPQL = entityManager.createQuery(REMOVE_BY_ID_QUERY);
            queryJPQL.setParameter("id", id);
            queryJPQL.executeUpdate();
        } catch (DataAccessException e) {
            throw new RuntimeException("Something went wrong in method removeUser(long id)", e);
        }
    }
}
