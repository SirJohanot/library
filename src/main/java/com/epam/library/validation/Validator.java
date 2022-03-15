package com.epam.library.validation;

import com.epam.library.exception.ValidationException;

/**
 * Interface concerned with validating objects that are about to be inserted into the database or updated
 *
 * @param <T> type of object
 */
public interface Validator<T> {

    /**
     * Validates the object
     *
     * @param object object to be validated
     * @throws ValidationException if the object is deemed invalid
     */
    void validate(T object) throws ValidationException;
}
