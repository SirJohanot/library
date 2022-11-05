package com.patiun.library.validation;

import com.patiun.library.exception.ValidationException;

import java.util.regex.Pattern;

public class PasswordValidator extends AbstractValidator<String> {

    private static final String PASSWORD_REGEX = "^[\\p{L}(0-9)]+$";

    @Override
    public void validate(String object) throws ValidationException {
        if (!Pattern.matches(PASSWORD_REGEX, object)) {
            throw new ValidationException("A password must consist of letters and numbers");
        }
    }
}
