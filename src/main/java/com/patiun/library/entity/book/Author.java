package com.patiun.library.entity.book;

import com.patiun.library.entity.Identifiable;

import java.io.Serializable;

public class Author implements Identifiable, Serializable {

    public static final String TABLE_NAME = "author";
    public static final String ID_COLUMN = "id";
    public static final String NAME_COLUMN = "name";

    private final Long id;
    private final String name;

    public Author(Long id, String name) {
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

    public static Author ofId(Long id) {
        return new Author(id, null);
    }

    public static Author ofName(String name) {
        return new Author(null, name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Author author = (Author) o;

        if (id != null ? !id.equals(author.id) : author.id != null) {
            return false;
        }
        return name != null ? name.equals(author.name) : author.name == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
