package com.epam.library.command.repository;

import com.epam.library.dao.Dao;
import com.epam.library.entity.Identifiable;

public interface Repository<T extends Identifiable> extends Dao<T> {
}
