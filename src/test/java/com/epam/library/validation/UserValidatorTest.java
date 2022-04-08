package com.epam.library.validation;

import com.epam.library.entity.User;
import com.epam.library.entity.enumeration.UserRole;
import com.epam.library.exception.ValidationException;
import org.junit.Test;

public class UserValidatorTest {

    private final Long id = 3L;
    private final String login = "Login";
    private final String name = "Name";
    private final String surname = "Surname";
    private final UserRole role = UserRole.READER;
    private final boolean blocked = false;

    private final UserValidator userValidator = new UserValidator();

    @Test(expected = ValidationException.class)
    public void testValidateShouldThrowExceptionIfLoginIsNull() throws ValidationException {
        //given
        User user = new User(id, null, name, surname, role, blocked);
        //when
        userValidator.validate(user);
        //then
    }

    @Test(expected = ValidationException.class)
    public void testValidateShouldThrowExceptionIfNameIsNull() throws ValidationException {
        //given
        User user = new User(id, login, null, surname, role, blocked);
        //when
        userValidator.validate(user);
        //then
    }

    @Test(expected = ValidationException.class)
    public void testValidateShouldThrowExceptionIfSurnameIsNull() throws ValidationException {
        //given
        User user = new User(id, login, name, null, role, blocked);
        //when
        userValidator.validate(user);
        //then
    }

    @Test(expected = ValidationException.class)
    public void testValidateShouldThrowExceptionIfRoleIsNull() throws ValidationException {
        //given
        User user = new User(id, login, name, surname, null, blocked);
        //when
        userValidator.validate(user);
        //then
    }

    @Test
    public void testValidateShouldNotThrowExceptionForCyrillicLogin() throws ValidationException {
        //given
        String login = "Логин";
        User user = new User(id, login, name, surname, role, blocked);
        //when
        userValidator.validate(user);
        //then
    }
}
