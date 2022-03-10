package com.epam.library.command.validation;

import com.epam.library.entity.Identifiable;
import com.epam.library.exception.ValidationException;

public interface Validator<T extends Identifiable> {

    void validate(T object) throws ValidationException;
}
