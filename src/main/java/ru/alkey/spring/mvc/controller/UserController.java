package ru.alkey.spring.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.alkey.spring.mvc.model.User;
import ru.alkey.spring.mvc.service.UserService;

import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private static final String REDIRECT_TO_USERS_PAGE_URL = "redirect:/users";
    private static final String REDIRECT_TO_USER_PAGE_URL = "redirect:/users/";

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public String createNewUser(@ModelAttribute User user) {
        User savedUser = userService.saveUser(user);
        System.out.println("Сохранён пользователь с id = " + savedUser.getId());
        return REDIRECT_TO_USER_PAGE_URL + savedUser.getId();
    }

    @GetMapping("/new")
    public String showUserCreationForm(@ModelAttribute User user) {
        return "user/new-user-form";
    }

    @PutMapping("/{id}")
    public String updateUser(@ModelAttribute User user, @PathVariable("id") long id) {
        userService.updateUser(user);
        System.out.println("Обновлён пользователь с id = " + id);
        return REDIRECT_TO_USERS_PAGE_URL;
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.removeUser(id);
        System.out.println("Удалён пользователь с id = " + id);
        return REDIRECT_TO_USERS_PAGE_URL;
    }

    @GetMapping("/{id}")
    public String showUserPage(Model model, @PathVariable("id") long id) {
        Optional<User> userFromBD = userService.findUser(id);
        userFromBD.ifPresent(user -> model.addAttribute("user", user));
        return "user/user-edit-form";
    }

    @GetMapping()
    public String showUsersPage(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "user/all-users";
    }
}
