package com.epam.library.mapper;

import com.epam.library.entity.Identifiable;
import com.epam.library.entity.User;

public class RowMapperFactory {

    public RowMapper<? extends Identifiable> createMapper(String tableName) {
        switch (tableName) {
            case User
                    .TABLE_NAME:
                return new UserRowMapper();
            default:
                throw new IllegalArgumentException("Could not identify table: " + tableName);
        }
    }
}
