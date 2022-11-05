package com.patiun.library.entity.book;

import com.patiun.library.entity.Identifiable;

import java.io.Serializable;

public class Genre implements Identifiable, Serializable {

    public static final String TABLE_NAME = "genre";
    public static final String ID_COLUMN = "id";
    public static final String NAME_COLUMN = "name";

    private final Long id;
    private final String name;

    public Genre(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static Genre ofId(Long id) {
        return new Genre(id, null);
    }

    public static Genre ofName(String name) {
        return new Genre(null, name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Genre genre = (Genre) o;

        if (id != null ? !id.equals(genre.id) : genre.id != null) {
            return false;
        }
        return name != null ? name.equals(genre.name) : genre.name == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
