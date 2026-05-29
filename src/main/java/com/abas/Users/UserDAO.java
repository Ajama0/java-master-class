package com.abas.Users;

import java.util.UUID;

public class UserDAO {
    private static User[] users = new User[2];


    static{
        users[0] = new User(UUID.randomUUID(), "Abas");
        users[1] = new User(UUID.randomUUID(), "james");

    }

    public User[] getUsers(){
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
