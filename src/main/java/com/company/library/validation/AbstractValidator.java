package com.company.library.validation;

import com.company.library.exception.ValidationException;

import java.util.regex.Pattern;

public abstract class AbstractValidator<T> implements Validator<T> {

    private static final String IS_A_WORD_REGEX = "[\\p{L}\\w]+.*";

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
