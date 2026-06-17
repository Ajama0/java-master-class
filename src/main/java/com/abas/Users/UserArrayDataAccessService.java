package com.abas.Users;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserArrayDataAccessService implements UserDAO{
    static List<User> users = new ArrayList<>();

    static {
        users.add(new User(UUID.fromString("8ca51d2b-aaaf-4bf2-834a-e02964e10fc3"), "Abas"));

        users.add(new User(UUID.fromString("b10d126a-3608-4980-9f9c-aa179f5cebc3"), "Jamila"));

    }


    public List<User> getUsers() {
        return users;
    }


    public boolean userExists(User user) {
        return users.stream().anyMatch(user1 -> user1.getId().equals(user.getId()));

    }


}
