package com.company.library.specification;

/**
 * Interface concerned with determining whether an object fits some defined specification
 *
 * @param <T> type of object
 */
public interface Specification<T> {

    /**
     * Determines whether the object fits into specification
     *
     * @param object object to evaluate
     * @return true - if object fits the specification. Otherwise, false
     */
    boolean isSpecified(T object);
}
