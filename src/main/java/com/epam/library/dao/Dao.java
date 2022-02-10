package com.epam.library.dao;

import com.epam.library.entity.Identifiable;

import java.util.List;
import java.util.Optional;

public interface Dao<T extends Identifiable> {

    Optional<T> getById(Long id);

    List<T> getAll();

    void save(T item);

    void removeById(Long id);
}
