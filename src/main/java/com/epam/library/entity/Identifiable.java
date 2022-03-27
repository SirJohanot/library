package com.epam.library.entity;

/**
 * Identifiable classes have IDs that are required to perform operations in the database
 */
public interface Identifiable {

    /**
     * Gets the object's ID
     *
     * @return Long value representing the ID of object
     */
    Long getId();
}
