package com.abas.User;

import java.util.Objects;
import java.util.UUID;

public class users {

    private UUID id;

    private String name;

    public users(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "users{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        users users = (users) o;
        return Objects.equals(id, users.id) && Objects.equals(name, users.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
