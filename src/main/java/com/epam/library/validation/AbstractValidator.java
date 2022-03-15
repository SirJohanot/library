package com.epam.library.validation;

import com.epam.library.exception.ValidationException;

import java.util.regex.Pattern;

public abstract class AbstractValidator<T> implements Validator<T> {

    private static final String IS_A_WORD_REGEX = "\\p{L}+.*";

    protected void throwExceptionIfNull(Object object, String exceptionMessage) throws ValidationException {
        if (object == null) {
            throw new ValidationException(exceptionMessage);
        }
    }

    protected void throwExceptionIfIsNotAWord(String line, String exceptionMessage) throws ValidationException {
        if (!Pattern.matches(IS_A_WORD_REGEX, line)) {
            throw new ValidationException(exceptionMessage);
        }
    }
}
