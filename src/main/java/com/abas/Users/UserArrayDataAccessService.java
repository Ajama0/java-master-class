package com.abas.Users;

import java.util.UUID;

public class UserArrayDataAccessService implements UserDAO{
    private static User[] users = new User[2];

    static {
        users[0] = new User(UUID.fromString("8ca51d2b-aaaf-4bf2-834a-e02964e10fc3"), "Abas");

        users[1] = new User(UUID.fromString("b10d126a-3608-4980-9f9c-aa179f5cebc3"), "Jamila");

    }


    public User[] getUsers() {
        return users;
    }


    public boolean userExists(User user) {
        for (User u : users) {
            if (u != null && u.equals(user)) {
                return true;
            }
        }
        return false;

    }


}
