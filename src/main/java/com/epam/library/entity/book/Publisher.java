package com.epam.library.entity.book;

import com.epam.library.entity.Identifiable;

import java.io.Serializable;

public class Publisher implements Identifiable, Serializable {

    public static final String TABLE_NAME = "publisher";
    public static final String ID_COLUMN = "id";
    public static final String NAME_COLUMN = "name";

    private final Long id;
    private final String name;

    public Publisher(Long id, String name) {
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

    public static Publisher ofId(Long id) {
        return new Publisher(id, null);
    }

    public static Publisher ofName(String name) {
        return new Publisher(null, name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Publisher publisher = (Publisher) o;

        if (id != null ? !id.equals(publisher.id) : publisher.id != null) {
            return false;
        }
        return name != null ? name.equals(publisher.name) : publisher.name == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Publisher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
