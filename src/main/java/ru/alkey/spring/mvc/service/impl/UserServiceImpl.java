package ru.alkey.spring.mvc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alkey.spring.mvc.dao.UserDAO;
import ru.alkey.spring.mvc.model.User;
import ru.alkey.spring.mvc.service.UserService;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAllUsers() {
        return userDAO.findAllUsers();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findUser(long id) {
        return userDAO.findUser(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findUser(String email) {
        return userDAO.findUser(email);
    }

    @Override
    @Transactional
    public User saveUser(User user) {
        return userDAO.saveUser(user);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        userDAO.updateUser(user);
    }

    @Override
    @Transactional
    public void removeUser(long id) {
        userDAO.removeUser(id);
    }
}
