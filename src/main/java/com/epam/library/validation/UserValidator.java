package com.epam.library.validation;

import com.epam.library.entity.User;
import com.epam.library.entity.enumeration.UserRole;
import com.epam.library.exception.ValidationException;

public class UserValidator extends AbstractValidator<User> {

    @Override
    public void validate(User object) throws ValidationException {
        String login = object.getLogin();
        throwExceptionIfNull(login, "A user's login cannot be null");

        String name = object.getName();
        throwExceptionIfNull(name, "A user's name cannot be null");
        throwExceptionIfIsNotAWord(name, "A user's name must start with an alphabetical character");

        String surname = object.getSurname();
        throwExceptionIfNull(surname, "A user's surname cannot be null");
        throwExceptionIfIsNotAWord(surname, "A user's surname must start with an alphabetical character");

        UserRole role = object.getRole();
        throwExceptionIfNull(role, "A user's role cannot be null");
    }
}
