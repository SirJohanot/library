package com.patiun.library.entity;

/**
 * Identifiable classes have IDs that are required to perform queries in the database
 */
public interface Identifiable {

    /**
     * Returns the object's ID
     *
     * @return Long value representing the ID of object
     */
    Long getId();
}
