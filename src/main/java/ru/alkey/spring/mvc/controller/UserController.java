package ru.alkey.spring.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;
import ru.alkey.spring.mvc.model.User;
import ru.alkey.spring.mvc.service.UserService;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final Validator userValidator;

    private static final String USER_CREATION_FORM_VIEW = "user/new-user-form";
    private static final String USER_EDIT_FORM_VIEW = "user/user-edit-form";
    private static final String ALL_USERS_VIEW = "user/all-users";
    private static final String REDIRECT_TO_USERS_PAGE_URL = "redirect:/users/";
    private static final String REDIRECT_TO_USER_CREATION_FORM_PAGE_URL = "redirect:/users/new";

    @Autowired
    public UserController(UserService userService, Validator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @PostMapping()
    public String createNewUser(@ModelAttribute @Valid User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            return USER_CREATION_FORM_VIEW;
        }

        User savedUser = userService.saveUser(user);
        System.out.println("Сохранён пользователь с id = " + savedUser.getId());
        return REDIRECT_TO_USERS_PAGE_URL + savedUser.getId();
    }

    @GetMapping("/new")
    public String showUserCreationForm(@ModelAttribute User user) {
        return USER_CREATION_FORM_VIEW;
    }

    @PutMapping("/{id}")
    public String updateUser(@ModelAttribute @Valid User user, BindingResult bindingResult,
                             @PathVariable("id") long id)
    {
        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            return USER_EDIT_FORM_VIEW;
        }

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
        return USER_EDIT_FORM_VIEW;
    }

    @GetMapping()
    public String showUsersPage(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return ALL_USERS_VIEW;
    }
}
