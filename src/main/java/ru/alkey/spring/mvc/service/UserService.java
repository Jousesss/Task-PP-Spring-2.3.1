package ru.alkey.spring.mvc.service;

import ru.alkey.spring.mvc.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAllUsers();
    Optional<User> findUser(long id);
    Optional<User> findUser(String email);
    User saveUser(User user);
    void updateUser(User user);
    void removeUser(long id);
}
