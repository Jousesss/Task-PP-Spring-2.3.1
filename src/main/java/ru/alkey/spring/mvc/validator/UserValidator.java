package ru.alkey.spring.mvc.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.alkey.spring.mvc.model.User;
import ru.alkey.spring.mvc.service.UserService;

import java.util.Optional;

@Component
public class UserValidator implements Validator {

    private final UserService userService;

    @Autowired
    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        if (!validateUsername(user.getUsername())) {
            errors.rejectValue("username", "",
                       "* Username must be between 2 and 36 characters!");
        }

        if (!validateEmail(user.getEmail(), user.getId())) {
            errors.rejectValue("email", "",
                    "* This email is already taken by somebody!");
        }
    }

    private boolean validateEmail(String email, long userId) {
        Optional<User> userFromBD = userService.findUser(email);

        if (userFromBD.isPresent()) {
            if (userFromBD.get().getId() != userId) {
                return false;
            }
        }

        return true;
    }

    private boolean validateUsername(String username) {
        String cleanedUsername = username.replaceAll(" ", "");

        if (cleanedUsername.length() > 1 && cleanedUsername.length() < 37) {
            return true;
        }

        return false;
    }
}
